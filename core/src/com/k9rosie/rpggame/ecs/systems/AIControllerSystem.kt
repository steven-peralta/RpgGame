package com.k9rosie.rpggame.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.k9rosie.rpggame.ecs.components.AIControlledComponent
import com.k9rosie.rpggame.ecs.components.ActionComponent
import com.k9rosie.rpggame.util.Actions
import ktx.ashley.allOf
import ktx.ashley.get
import kotlin.random.Random

class AIControllerSystem : IteratingSystem(
        allOf(AIControlledComponent::class, ActionComponent::class).get()
){
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[ActionComponent.mapper]?.let { action ->

        }

    }
}