package net.drshoggoth.games.drshoggoth.scenes

import net.drshoggoth.games.drshoggoth.responses.UpdateResponse

interface Scene {
    fun create()
    fun update(): UpdateResponse?
    fun render()
    fun dispose()
}