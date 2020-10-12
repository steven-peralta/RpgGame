package com.k9rosie.rpggame.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.tiled.TiledMap
import ktx.assets.getAsset
import ktx.assets.load

enum class MapAssets(val path: String) {
    TestMap("maps/TestMap.tmx")
}

inline fun AssetManager.load(asset: MapAssets) = load<TiledMap>(asset.path)
inline operator fun AssetManager.get(asset: MapAssets) = getAsset<TiledMap>(asset.path)