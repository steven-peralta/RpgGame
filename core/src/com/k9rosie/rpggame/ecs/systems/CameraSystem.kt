package com.k9rosie.rpggame.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.k9rosie.rpggame.Game
import com.k9rosie.rpggame.ecs.components.CameraLockedComponent
import com.k9rosie.rpggame.ecs.components.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get

class CameraSystem(private val viewport: ExtendViewport) : IteratingSystem(
        allOf(CameraLockedComponent::class, TransformComponent::class).get()
) {
    override fun update(deltaTime: Float) {
        super.update(deltaTime)
        viewport.camera.update()
    }
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[TransformComponent.mapper]?.let { transform ->
            viewport.camera.position.x = transform.bounds.x
            viewport.camera.position.y = transform.bounds.y
        }
    }
}