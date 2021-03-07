package com.k9rosie.rpggame

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor

class InputQueue : InputProcessor {
    val queue = linkedSetOf<Int>()

    override fun keyDown(keycode: Int): Boolean {
        return queue.add(keycode)
    }

    override fun keyUp(keycode: Int): Boolean {
        return queue.remove(keycode)
    }

    override fun keyTyped(character: Char): Boolean {
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return true
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return true
    }
}