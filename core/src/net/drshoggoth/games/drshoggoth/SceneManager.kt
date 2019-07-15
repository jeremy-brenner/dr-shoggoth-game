package net.drshoggoth.games.drshoggoth

import net.drshoggoth.games.drshoggoth.responses.UpdateResponse
import net.drshoggoth.games.drshoggoth.scenes.*

object SceneManager {
    var current = listOf("loading")

    private val scenes = mapOf(
            "loading" to LoadingScene,
            "title" to TitleScene,
            "game" to GameScene,
            "background" to BackgroundScene,
            "scoreboard" to ScoreboardScene
    )

    fun currentScenes() = current.map { scenes[it] }.filterNotNull()

    fun create() {
        scenes.map { it.value.create() }
    }

    fun update(): List<UpdateResponse> {
        return currentScenes().map { it.update() }.filterNotNull()
    }

    fun render() {
        currentScenes().forEach { it.render() }
    }

    fun dispose() {
        scenes.map { it.value.dispose() }
    }

}