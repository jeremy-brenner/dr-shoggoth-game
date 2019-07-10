package net.drshoggoth.games.drshoggoth

import net.drshoggoth.games.drshoggoth.scenes.GameScene
import net.drshoggoth.games.drshoggoth.scenes.LoadingScene
import net.drshoggoth.games.drshoggoth.scenes.TitleScene

class SceneManager {
    var current = "loading"

    private val scenes = mapOf(
            "loading" to LoadingScene(),
            "title" to TitleScene(),
            "game" to GameScene()
    )

    fun doneLoading() {
        scenes.map { it.value.doneLoading() }
    }

    fun render() {
        scenes[current]?.let {
            it.handleInput()
            it.render()
        }
    }

    fun dispose() {
        scenes.map { it.value.dispose() }
    }

}