package net.drshoggoth.games.drshoggoth

import net.drshoggoth.games.drshoggoth.scenes.*

object SceneManager {
    var current: List<Scene> = listOf(LoadingScene)

    private val scenes = listOf(
            LoadingScene,
            TitleScene,
            GameScene,
            BackgroundScene,
            ScoreboardScene,
            PauseScene
    )

    fun create() {
        scenes.map { it.create() }
    }

    fun update(): List<SceneResponse> {
        return current.map { it.update() }
    }

    fun render() {
        current.forEach { it.render() }
    }

    fun dispose() {
        scenes.map { it.dispose() }
    }

}