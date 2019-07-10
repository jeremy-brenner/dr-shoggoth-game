package net.drshoggoth.games.drshoggoth

class SceneManager {
    var current = "loading"

    private val scenes = mapOf(
            "loading" to LoadingScene(),
            "title" to TitleScene(),
            "game" to GameScene()
    )

    fun create() {
        scenes.map { it.value.create() }
    }

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