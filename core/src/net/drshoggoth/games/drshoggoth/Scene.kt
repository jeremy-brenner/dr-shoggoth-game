package net.drshoggoth.games.drshoggoth

interface Scene {
    fun create()
    fun handleInput()
    fun render()
    fun dispose()
}