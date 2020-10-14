package com.k9rosie.rpggame.desktop

import com.badlogic.gdx.Application
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.k9rosie.rpggame.Game

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration().apply {
            width = 800
            height = 600
            title = "Untitled RPG Game"
        }
        LwjglApplication(Game(), config).logLevel = Application.LOG_ERROR
    }
}