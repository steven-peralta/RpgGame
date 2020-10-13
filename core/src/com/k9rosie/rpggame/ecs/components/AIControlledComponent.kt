package com.k9rosie.rpggame.ecs.components

import com.badlogic.ashley.core.Component
import ktx.ashley.mapperFor

class AIControlledComponent : Component {
    companion object {
        val mapper = mapperFor<AIControlledComponent>()
    }
}