package com.k9rosie.rpggame.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.k9rosie.rpggame.Game
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
                 private val world: World
) : KtxScreen {
    private lateinit var map: TiledMap

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun show() {
        (viewport.camera as OrthographicCamera).setToOrtho(false, Game.virtualWidth, Game.virtualHeight)
        batch.projectionMatrix = viewport.camera.combined

        map = assets[MapAssets.TestMap]
        engine.apply {
            addSystem(CameraSystem(viewport))
            addSystem(AnimationSystem())
            addSystem(RenderSystem(batch, viewport, map))
            addSystem(PhysicsSystem(world))
            addSystem(PhysicsDebugSystem(world, viewport))
        }

        map.layers["collisions"].objects.getByType(RectangleMapObject::class.java).forEach {
            engine.entity {
                with<BodyComponent> {
                    body = world.createBody(BodyDef().apply {
                        type = BodyDef.BodyType.StaticBody
                        position.set(it.rectangle.x, it.rectangle.y)
                    })
                    body.createFixture(FixtureDef().apply {
                        shape = PolygonShape().apply { setAsBox(it.rectangle.width, it.rectangle.height) }
                        friction = 0.4f
                        density = 20f
                        restitution = 0.6f
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
                    position.set(16f * 3, 16f * 3)
                })
                body.applyAngularImpulse(50f, true)
                body.createFixture(FixtureDef().apply {
                    shape = PolygonShape().apply { setAsBox(14f, 21f) }
                    friction = 0.4f
                    density = 20f
                    restitution = 0.6f
                })
            }
            with<TextureComponent> {
                texture = assets[TextureAtlasAssets.Character].findRegion("north_idle")
            }
            with<TransformComponent> {
                bounds.x = 16f * 3
                bounds.y = 16f * 3
                bounds.width = 14f
                bounds.height = 21f
            }
            with<CameraLockedComponent>()
            with<AnimationComponent> {
                currentAnimation = AnimationStates.WALKING_EAST
                animations = mapOf(
                        AnimationStates.IDLE_NORTH to Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("north_idle")),
                        AnimationStates.IDLE_EAST to Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("east_idle")),
                        AnimationStates.IDLE_WEST to Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("west_idle")),
                        AnimationStates.IDLE_SOUTH to Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("south_idle")),
                        AnimationStates.WALKING_NORTH to Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("north_running"), Animation.PlayMode.LOOP_PINGPONG),
                        AnimationStates.WALKING_SOUTH to Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("south_running"), Animation.PlayMode.LOOP_PINGPONG),
                        AnimationStates.WALKING_EAST to Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("east_running"), Animation.PlayMode.LOOP_PINGPONG),
                        AnimationStates.WALKING_WEST to Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("west_running"), Animation.PlayMode.LOOP_PINGPONG)
                )
            }
        }

        // engine.entity {
        //     with<TextureComponent> {
        //         texture = assets[TextureAtlasAssets.Character].findRegion("north_idle")
        //     }
        //     with<TransformComponent> {
        //         bounds.x = 10f
        //         bounds.y = 0f
        //         bounds.width = 14f
        //         bounds.height = 21f
        //     }
        //     with<AnimationComponent> {
        //         currentAnimation = AnimationStates.WALKING_WEST
        //         animations = mapOf(
        //                 AnimationStates.IDLE_NORTH to Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("north_idle")),
        //                 AnimationStates.IDLE_EAST to Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("east_idle")),
        //                 AnimationStates.IDLE_WEST to Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("west_idle")),
        //                 AnimationStates.IDLE_SOUTH to Animation<TextureRegion>(0f, assets[TextureAtlasAssets.Character].findRegion("south_idle")),
        //                 AnimationStates.WALKING_NORTH to Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("north_running"), Animation.PlayMode.LOOP_PINGPONG),
        //                 AnimationStates.WALKING_SOUTH to Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("south_running"), Animation.PlayMode.LOOP_PINGPONG),
        //                 AnimationStates.WALKING_EAST to Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("east_running"), Animation.PlayMode.LOOP_PINGPONG),
        //                 AnimationStates.WALKING_WEST to Animation<TextureRegion>(1/8f, assets[TextureAtlasAssets.Character].findRegions("west_running"), Animation.PlayMode.LOOP_PINGPONG)
        //         )
        //     }
        // }
    }

    override fun render(delta: Float) {
        engine.update(delta)
    }
}