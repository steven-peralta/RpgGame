package com.k9rosie.rpggame.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.Body
import ktx.ashley.mapperFor

class BodyComponent : Component {
    companion object {
        val mapper = mapperFor<BodyComponent>()
    }

    lateinit var body: Body
}