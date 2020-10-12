package com.k9rosie.rpggame.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Rectangle
import ktx.ashley.mapperFor

class TransformComponent(val bounds: Rectangle = Rectangle()) : Component {
    companion object {
        val mapper = mapperFor<TransformComponent>()
    }
}