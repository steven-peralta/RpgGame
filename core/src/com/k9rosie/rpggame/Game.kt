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
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.k9rosie.rpggame.net.SocketBuilder
import com.k9rosie.rpggame.screens.GameScreen
import com.k9rosie.rpggame.screens.LoadingScreen
import com.kotcrab.vis.ui.VisUI
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.Dispatchers
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.inject.Context
import ktx.inject.register
import ktx.scene2d.Scene2DSkin

class Game : KtxGame<KtxScreen>() {
    companion object {
        const val width = 800f
        const val height = 600f
    }
    private val ctx = Context()

    override fun create() {
        val inputQueue = InputQueue()
        val spriteBatch = SpriteBatch()
        val font = BitmapFont()
        val assetManager = AssetManager().apply {
            setLoader(TiledMap::class.java, TmxMapLoader(InternalFileHandleResolver()))
        }
        val viewport = ExtendViewport(width, height)
        val scene = Stage(viewport)
        val entityEngine = PooledEngine()
        val box2dWorld = World(Vector2(0f, 0f), true)
        val socketBuilder = SocketBuilder()
        ctx.register {
            bindSingleton(this@Game)
            bindSingleton<Batch>(spriteBatch)
            bindSingleton(font)
            bindSingleton(assetManager)
            bindSingleton(inputQueue)
            bindSingleton(viewport)
            bindSingleton(scene)
            bindSingleton(entityEngine)
            bindSingleton(box2dWorld)
            bindSingleton(socketBuilder)

            addScreen(LoadingScreen(inject(), inject(), inject(), inject()))
            addScreen(GameScreen(inject(), inject(), inject(), inject(), inject(), inject()))
        }

        VisUI.load()
        Scene2DSkin.defaultSkin = VisUI.getSkin()

        setScreen<LoadingScreen>()
        super.create()
    }

    override fun dispose() {
        ctx.remove<Game>()
        ctx.dispose()
        super.dispose()
    }
}