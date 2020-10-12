package com.k9rosie.rpggame.ecs.components

import com.badlogic.ashley.core.Component
import ktx.ashley.mapperFor

class CameraLockedComponent : Component {
    companion object {
        val mapper = mapperFor<CameraLockedComponent>()
    }
}