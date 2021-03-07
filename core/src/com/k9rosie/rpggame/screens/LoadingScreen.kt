package com.k9rosie.rpggame.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.k9rosie.rpggame.Game
import com.k9rosie.rpggame.InputQueue
import com.k9rosie.rpggame.assets.MapAssets
import com.k9rosie.rpggame.assets.TextureAtlasAssets
import com.k9rosie.rpggame.assets.load
import com.k9rosie.rpggame.net.SocketBuilder
import com.kotcrab.vis.ui.VisUI
import ktx.app.KtxScreen
import ktx.graphics.use
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.actors
import ktx.scene2d.scene2d
import ktx.scene2d.vis.*

class LoadingScreen(private val game: Game,
                    private val assets: AssetManager,
                    private val stage: Stage,
                    private val socketBuilder: SocketBuilder
) : KtxScreen {

    override fun show() {
        Gdx.input.inputProcessor = stage

        stage.actors {
            visTable(defaultSpacing = true) {
                validator {
                    defaults().left()

                    visLabel("Hostname: ")
                    notEmpty(visValidatableTextField().cell(grow = true), "Hostname can't be empty")
                    row()

                    visLabel("Port: ")
                    val portField = visValidatableTextField().cell(grow = true)
                    notEmpty(portField, "Port can't be empty")
                    integerNumber(portField, "Port must be number")
                    row()

                    setMessageLabel(visLabel("").cell(minWidth = 200f))
                    addDisableTarget(visTextButton("Connect").cell(align = Align.right))
                }
                pack()
                setPosition(Game.width / 2 - width / 2, Game.height / 2 - height / 2)
            }
        }

        TextureAtlasAssets.values().forEach { assets.load(it) }
    }

    override fun resize(width: Int, height: Int) {
        // See below for what true means.
        stage.viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(delta)
        stage.draw()

        // assets.update()

        // batch.use {
        //     font.draw(batch, "Loading...", 0f, 0f)
        // }

        // if (assets.isFinished) {
        //     game.removeScreen<LoadingScreen>()
        //     game.setScreen<GameScreen>()
        // }
    }
}