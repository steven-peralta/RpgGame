package com.k9rosie.rpggame.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.k9rosie.rpggame.util.AnimationStates
import ktx.ashley.mapperFor

class AnimationComponent(
        var currentAnimation: AnimationStates = AnimationStates.IDLE_NORTH,
        var time: Float = 0.0F,
        // regarding the pair: the first part is the list of animation sprites that get played
        // the second part is the next animation that gets played when the animation is finished
        var animations: Map<AnimationStates, Pair<Animation<TextureRegion>, AnimationStates>> = mapOf()
) : Component {
    companion object {
        val mapper = mapperFor<AnimationComponent>()
    }
}