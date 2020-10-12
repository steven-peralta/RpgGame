package com.k9rosie.rpggame.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.Map
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.k9rosie.rpggame.Game
import com.k9rosie.rpggame.ecs.components.TextureComponent
import com.k9rosie.rpggame.ecs.components.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use

class RenderSystem(private val batch: Batch, private val viewport: ExtendViewport, map: Map) : SortedIteratingSystem (
        allOf(TransformComponent::class, TextureComponent::class).get(),
        compareBy { it[TextureComponent.mapper]?.zIndex }
) {
    private val mapRenderer: OrthogonalTiledMapRenderer = OrthogonalTiledMapRenderer(map as TiledMap?, Game.unitScale, batch).apply { setView(viewport.camera as OrthographicCamera) }
    private val entityLayer = map.layers.getIndex("entities")
    private val bgLayers = (0 until entityLayer).toList().toIntArray()
    private val fgLayers = ((entityLayer + 1) until map.layers.size()).toList().toIntArray()

    override fun update(deltaTime: Float) {
        mapRenderer.render(bgLayers)
        batch.projectionMatrix = viewport.camera.combined
        batch.use {
            super.update(deltaTime)
        }
        mapRenderer.render(fgLayers)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[TransformComponent.mapper]?.let { transform ->
            entity[TextureComponent.mapper]?.let { texture ->
                batch.draw(texture.texture, transform.bounds.x * (Game.pixelSize / Game.virtualWidth),
                        transform.bounds.y * (Game.pixelSize / Game.virtualHeight),
                        texture.texture.regionWidth.toFloat() * (Game.pixelSize / Game.virtualWidth),
                        texture.texture.regionHeight.toFloat() * (Game.pixelSize / Game.virtualWidth))
            }
        }
    }
}