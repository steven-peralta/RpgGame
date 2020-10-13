package com.k9rosie.rpggame.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.k9rosie.rpggame.ecs.components.ActionComponent
import com.k9rosie.rpggame.ecs.components.PlayerControlledComponent
import com.k9rosie.rpggame.util.Actions
import ktx.ashley.allOf
import ktx.ashley.get

class PlayerControllerSystem : IteratingSystem(
        allOf(PlayerControlledComponent::class, ActionComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[ActionComponent.mapper]?.let { action ->
            when {
                Gdx.input.isKeyPressed(Input.Keys.RIGHT) -> action.action = Actions.MOVE_RIGHT
                Gdx.input.isKeyPressed(Input.Keys.LEFT) -> action.action = Actions.MOVE_LEFT
                Gdx.input.isKeyPressed(Input.Keys.UP) -> action.action = Actions.MOVE_UP
                Gdx.input.isKeyPressed(Input.Keys.DOWN) -> action.action = Actions.MOVE_DOWN
                else -> action.action = Actions.NOTHING
            }
        }
    }
}