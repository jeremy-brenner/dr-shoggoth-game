package net.drshoggoth.games.drshoggoth.scenes

import net.drshoggoth.games.drshoggoth.SceneResponse

interface Scene {
    fun create()
    fun update(): SceneResponse
    fun render()
    fun dispose()
}