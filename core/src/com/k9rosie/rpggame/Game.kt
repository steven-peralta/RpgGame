package com.k9rosie.rpggame

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.k9rosie.rpggame.screens.GameScreen
import com.k9rosie.rpggame.screens.LoadingScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.inject.Context
import ktx.inject.register

class Game : KtxGame<KtxScreen>() {
    companion object {
        val height = 20f
        val ppu = 600 / height
        val width = 800 / ppu
    }
    private val ctx = Context()

    override fun create() {
        ctx.register {
            bindSingleton(this@Game)
            bindSingleton<Batch>(SpriteBatch())
            bindSingleton(BitmapFont())
            bindSingleton(AssetManager().apply {
                setLoader(TiledMap::class.java, TmxMapLoader(InternalFileHandleResolver()))
            })
            bindSingleton(OrthographicCamera(width, height))
            bindSingleton(PooledEngine())
            bindSingleton(World(Vector2(0f, 0f), true))

            addScreen(LoadingScreen(inject(), inject(), inject(), inject(), inject()))
            addScreen(GameScreen(inject(), inject(), inject(), inject(), inject()))
        }

        setScreen<LoadingScreen>()
        super.create()
    }

    override fun dispose() {
        ctx.remove<Game>()
        ctx.dispose()
        super.dispose()
    }
}