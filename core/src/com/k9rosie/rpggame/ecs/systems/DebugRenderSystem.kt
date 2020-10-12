package com.k9rosie.rpggame.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.k9rosie.rpggame.ecs.components.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use

class DebugRenderSystem(private val camera: OrthographicCamera) : IteratingSystem(
        allOf(TransformComponent::class).get()
) {
    private val shapeRenderer = ShapeRenderer()

    override fun update(deltaTime: Float) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            it.projectionMatrix = camera.combined
            super.update(deltaTime)
        }

    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[TransformComponent.mapper]?.let { transform ->
            shapeRenderer.rect(transform.bounds.x, transform.bounds.y, transform.bounds.width, transform.bounds.height)
        }
    }
}