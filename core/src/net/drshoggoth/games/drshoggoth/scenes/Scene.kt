package net.drshoggoth.games.drshoggoth.scenes

interface Scene {
    fun doneLoading()
    fun handleInput()
    fun render()
    fun dispose()
}