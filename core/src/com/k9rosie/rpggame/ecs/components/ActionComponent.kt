package com.k9rosie.rpggame.ecs.components

import com.badlogic.ashley.core.Component
import com.k9rosie.rpggame.util.Actions
import ktx.ashley.mapperFor

class ActionComponent : Component {
    companion object {
        val mapper = mapperFor<ActionComponent>()
    }
    var action = Actions.NOTHING
}