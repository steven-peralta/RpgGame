package com.k9rosie.rpggame.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.k9rosie.rpggame.ecs.components.ActionComponent
import com.k9rosie.rpggame.ecs.components.AnimationComponent
import com.k9rosie.rpggame.ecs.components.BodyComponent
import com.k9rosie.rpggame.ecs.components.PlayerControlledComponent
import com.k9rosie.rpggame.util.Actions
import com.k9rosie.rpggame.util.AnimationStates
import ktx.ashley.allOf
import ktx.ashley.get

class MovementSystem : IteratingSystem(
        allOf(ActionComponent::class, BodyComponent::class, AnimationComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[ActionComponent.mapper]?.let { action ->
            entity[BodyComponent.mapper]?.let { body ->
                entity[AnimationComponent.mapper]?.let { animation ->
                    when (action.action) {
                        Actions.NOTHING -> { body.body.setLinearVelocity(0f, 0f) }
                        Actions.MOVE_LEFT -> { body.body.setLinearVelocity(-100f, 0f); animation.currentAnimation = AnimationStates.WALKING_WEST }
                        Actions.MOVE_RIGHT -> { body.body.setLinearVelocity(100f, 0f); animation.currentAnimation = AnimationStates.WALKING_EAST }
                        Actions.MOVE_DOWN -> { body.body.setLinearVelocity(0f, -100f); animation.currentAnimation = AnimationStates.WALKING_SOUTH }
                        Actions.MOVE_UP ->  { body.body.setLinearVelocity(0f, 100f); animation.currentAnimation = AnimationStates.WALKING_NORTH }
                    }
                }

            }
        }
    }

}