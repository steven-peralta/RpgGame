package com.k9rosie.rpggame.screens

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.k9rosie.rpggame.Game
import com.k9rosie.rpggame.assets.MapAssets
import com.k9rosie.rpggame.assets.TextureAtlasAssets
import com.k9rosie.rpggame.assets.load
import ktx.app.KtxScreen
import ktx.graphics.use

class LoadingScreen(private val game: Game,
                    private val batch: Batch,
                    private val font: BitmapFont,
                    private val assets: AssetManager,
                    private val viewport: ExtendViewport
) : KtxScreen {
    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun show() {
        MapAssets.values().forEach { assets.load(it) }
        TextureAtlasAssets.values().forEach { assets.load(it) }
    }

    override fun render(delta: Float) {
        assets.update()

        batch.use {
            font.draw(batch, "Loading...", Game.windowWidth/2f, Game.windowHeight/2f)
        }

        if (assets.isFinished) {
            game.removeScreen<LoadingScreen>()
            game.setScreen<GameScreen>()
        }
    }
}