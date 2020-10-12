package com.k9rosie.rpggame.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.ashley.mapperFor

class TextureComponent(var texture: TextureRegion = TextureRegion(), var zIndex: Int = 0) : Component {
    companion object {
        val mapper = mapperFor<TextureComponent>()
    }
}