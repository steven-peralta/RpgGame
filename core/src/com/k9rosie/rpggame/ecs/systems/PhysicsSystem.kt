package com.k9rosie.rpggame.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d.World
import com.k9rosie.rpggame.ecs.components.BodyComponent
import com.k9rosie.rpggame.ecs.components.TransformComponent
import ktx.ashley.allOf

class PhysicsSystem(private val world: World) : IteratingSystem(
        allOf(BodyComponent::class, TransformComponent::class).get()
) {
    companion object {
        const val maxStepTime = 1/165f
        var accumulator = 0f
    }

    private val bodiesQueue: MutableSet<Entity> = mutableSetOf()

    override fun update(deltaTime: Float) {
        super.update(deltaTime)
        val frameTime = deltaTime.coerceAtMost(0.25f)
        accumulator += frameTime
        if (accumulator >= maxStepTime) {
            world.step(maxStepTime, 6, 2);
            accumulator -= maxStepTime
            bodiesQueue.forEach {
                val transform = TransformComponent.mapper[it]
                val body = BodyComponent.mapper[it]
                val pos = body.body.position
                transform.bounds.x = pos.x - transform.bounds.width / 2
                transform.bounds.y = pos.y - transform.bounds.height / 2
            }
        }
        bodiesQueue.clear()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        bodiesQueue.add(entity)
    }
}