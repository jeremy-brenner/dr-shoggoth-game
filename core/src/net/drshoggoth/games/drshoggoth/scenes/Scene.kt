package net.drshoggoth.games.drshoggoth.scenes

interface Scene {
    fun doneLoading()
    fun update()
    fun render()
    fun dispose()
}