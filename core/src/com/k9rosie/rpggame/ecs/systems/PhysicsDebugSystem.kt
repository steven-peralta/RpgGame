package com.k9rosie.rpggame.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.ExtendViewport
import ktx.ashley.allOf

class PhysicsDebugSystem(private val world: World, private val camera: OrthographicCamera) : IteratingSystem(
        allOf().get()
) {
    private val debugRenderer = Box2DDebugRenderer()

    override fun update(deltaTime: Float) {
        super.update(deltaTime)
        debugRenderer.render(world, camera.combined)
    }
    override fun processEntity(entity: Entity, deltaTime: Float) {

    }
}