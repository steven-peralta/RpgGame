package com.k9rosie.rpggame.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Sprite
import com.k9rosie.rpggame.ecs.components.AnimationComponent
import com.k9rosie.rpggame.ecs.components.TextureComponent
import ktx.ashley.allOf
import ktx.ashley.get

class AnimationSystem : IteratingSystem(
        allOf(AnimationComponent::class, TextureComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[AnimationComponent.mapper]?.let { animation ->
            entity[TextureComponent.mapper]?.let { texture ->
                if (animation.animations.containsKey(animation.currentAnimation)) {
                    texture.texture = animation.animations[animation.currentAnimation]?.first?.getKeyFrame(animation.time)
                            ?: error("current animation isn't defined in animations list")
                    animation.time += deltaTime

                }
            }
        }
    }
}