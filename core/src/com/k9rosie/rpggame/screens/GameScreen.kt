package com.k9rosie.rpggame.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.k9rosie.rpggame.InputQueue
import com.k9rosie.rpggame.assets.MapAssets
import com.k9rosie.rpggame.assets.TextureAtlasAssets
import com.k9rosie.rpggame.assets.get
import com.k9rosie.rpggame.ecs.components.*
import com.k9rosie.rpggame.ecs.systems.*
import com.k9rosie.rpggame.util.AnimationStates
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.ashley.with

class GameScreen(private val batch: Batch,
                 private val viewport: ExtendViewport,
                 private val engine: PooledEngine,
                 private val assets: AssetManager,
                 private val world: World,
                 private val input: InputQueue
) : KtxScreen {
    private lateinit var map: TiledMap

    override fun show() {

        map = assets[MapAssets.TestMap]
        engine.apply {
            addSystem(CameraSystem(viewport))
            addSystem(AnimationSystem())
            addSystem(PlayerControllerSystem(input))
            addSystem(AIControllerSystem())
            addSystem(MovementSystem())
            addSystem(RenderSystem(batch, viewport, map))
            addSystem(PhysicsSystem(world))
        }

        map.layers["collisions"].objects.getByType(RectangleMapObject::class.java).forEach {
            engine.entity {
                with<BodyComponent> {
                    body = world.createBody(BodyDef().apply {
                        type = BodyDef.BodyType.StaticBody
                        position.set((it.rectangle.x + it.rectangle.width * 0.5f), (it.rectangle.y + it.rectangle.height * 0.5f))
                    })
                    body.createFixture(FixtureDef().apply {
                        shape = PolygonShape().apply {
                            setAsBox(it.rectangle.width * 0.5f, it.rectangle.height * 0.5f)
                        }
                    })
                }
                with<TransformComponent> {
                    bounds.x = it.rectangle.x
                    bounds.y = it.rectangle.y
                    bounds.width = it.rectangle.width
                    bounds.height = it.rectangle.height
                }
            }
        }

        val player = engine.entity {
            with<BodyComponent> {
                body = world.createBody(BodyDef().apply {
                    type = BodyDef.BodyType.DynamicBody
                    position.set((17 + 14 * 0.5f), (17 + 21 * 0.5f))
                    fixedRotation = true
                })
                body.createFixture(FixtureDef().apply {
                    shape = PolygonShape().apply {
                        setAsBox(14f/2, 21f/2)
                    }
                })
            }
            with<TextureComponent> {
                texture = assets[TextureAtlasAssets.Character].findRegion("north_idle")
            }
            with<ActionComponent>()
            with<PlayerControlledComponent>()
            with<TransformComponent> {
                bounds.x = 17f
                bounds.y = 17f
                bounds.width = 14f
                bounds.height = 21f
            }
            with<CameraLockedComponent>()
            with<AnimationComponent> {
                currentAnimation = AnimationStates.IDLE_EAST
                animations = mapOf(
                        AnimationStates.IDLE_NORTH to Pair(Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("north_idle")), AnimationStates.IDLE_NORTH),
                        AnimationStates.IDLE_EAST to Pair(Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("east_idle")), AnimationStates.IDLE_EAST),
                        AnimationStates.IDLE_WEST to Pair(Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("west_idle")), AnimationStates.IDLE_WEST),
                        AnimationStates.IDLE_SOUTH to Pair(Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("south_idle")), AnimationStates.IDLE_SOUTH),
                        AnimationStates.WALKING_NORTH to Pair(Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("north_running"), Animation.PlayMode.LOOP_PINGPONG), AnimationStates.IDLE_NORTH),
                        AnimationStates.WALKING_SOUTH to Pair(Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("south_running"), Animation.PlayMode.LOOP_PINGPONG), AnimationStates.IDLE_SOUTH),
                        AnimationStates.WALKING_EAST to Pair(Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("east_running"), Animation.PlayMode.LOOP_PINGPONG), AnimationStates.IDLE_EAST),
                        AnimationStates.WALKING_WEST to Pair(Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("west_running"), Animation.PlayMode.LOOP_PINGPONG), AnimationStates.IDLE_WEST)
                )
            }
        }

        engine.entity {
            with<BodyComponent> {
                body = world.createBody(BodyDef().apply {
                    type = BodyDef.BodyType.DynamicBody
                    position.set((60 + 14 * 0.5f), (17 + 21 * 0.5f))
                    fixedRotation = true
                })
                body.createFixture(FixtureDef().apply {
                    shape = PolygonShape().apply {
                        setAsBox(14f/2, 21f/2)
                    }
                })
            }
            with<TextureComponent> {
                texture = assets[TextureAtlasAssets.Character].findRegion("north_idle")
            }
            with<TransformComponent> {
                bounds.x = 60f
                bounds.y = 17f
                bounds.width = 14f
                bounds.height = 21f
            }
            with<ActionComponent>()
            with<AIControlledComponent>()
            with<AnimationComponent> {
                currentAnimation = AnimationStates.IDLE_WEST
                animations = mapOf(
                        AnimationStates.IDLE_NORTH to Pair(Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("north_idle")), AnimationStates.IDLE_NORTH),
                        AnimationStates.IDLE_EAST to Pair(Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("east_idle")), AnimationStates.IDLE_EAST),
                        AnimationStates.IDLE_WEST to Pair(Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("west_idle")), AnimationStates.IDLE_WEST),
                        AnimationStates.IDLE_SOUTH to Pair(Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("south_idle")), AnimationStates.IDLE_SOUTH),
                        AnimationStates.WALKING_NORTH to Pair(Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("north_running"), Animation.PlayMode.LOOP_PINGPONG), AnimationStates.IDLE_NORTH),
                        AnimationStates.WALKING_SOUTH to Pair(Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("south_running"), Animation.PlayMode.LOOP_PINGPONG), AnimationStates.IDLE_SOUTH),
                        AnimationStates.WALKING_EAST to Pair(Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("east_running"), Animation.PlayMode.LOOP_PINGPONG), AnimationStates.IDLE_EAST),
                        AnimationStates.WALKING_WEST to Pair(Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("west_running"), Animation.PlayMode.LOOP_PINGPONG), AnimationStates.IDLE_WEST)
                )
            }
        }
    }

    override fun render(delta: Float) {
        engine.update(delta)
    }
}