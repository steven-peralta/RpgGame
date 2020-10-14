package com.k9rosie.rpggame.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Input
import com.k9rosie.rpggame.InputQueue
import com.k9rosie.rpggame.ecs.components.ActionComponent
import com.k9rosie.rpggame.ecs.components.PlayerControlledComponent
import com.k9rosie.rpggame.util.Actions
import ktx.ashley.allOf
import ktx.ashley.get

class PlayerControllerSystem(val input: InputQueue) : IteratingSystem(
        allOf(PlayerControlledComponent::class, ActionComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[ActionComponent.mapper]?.let { action ->
            if (input.queue.size == 0) {
                action.action = Actions.NOTHING
            } else {
                input.queue.forEach {
                    when (it) {
                        Input.Keys.RIGHT -> action.action = Actions.MOVE_RIGHT
                        Input.Keys.LEFT -> action.action = Actions.MOVE_LEFT
                        Input.Keys.UP -> action.action = Actions.MOVE_UP
                        Input.Keys.DOWN -> action.action = Actions.MOVE_DOWN
                    }
                }
            }
        }
    }
}