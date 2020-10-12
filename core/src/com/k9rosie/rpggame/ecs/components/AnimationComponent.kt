package com.k9rosie.rpggame.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.k9rosie.rpggame.util.AnimationStates
import ktx.ashley.mapperFor

class AnimationComponent(
        var currentAnimation: AnimationStates = AnimationStates.IDLE_NORTH,
        var time: Float = 0.0F,
        var animations: Map<AnimationStates, Animation<TextureRegion>> = mapOf()
) : Component {
    companion object {
        val mapper = mapperFor<AnimationComponent>()
    }
}