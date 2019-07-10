package net.drshoggoth.games.drshoggoth

interface Scene {
    fun create()
    fun doneLoading()
    fun handleInput()
    fun render()
    fun dispose()
}