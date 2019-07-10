package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.ApplicationAdapter
import net.drshoggoth.games.drshoggoth.Assets.assets


class DrShoggothGame : ApplicationAdapter() {
    private var loading: Boolean = true
    private val scenes = mapOf(
            "loading" to LoadingScene(),
            "title" to TitleScene(),
            "game" to GameScene()
    )
    private var currentScene = "loading"

    override fun create() {
        PillLoader.load()
    }

    private fun doneLoading() {
        println("did a thing")
        PillModels.pillModels = PillLoader.get()
        scenes.map { it.value.create() }
        currentScene = "game"
        loading = false
    }

    override fun resize(width: Int, height: Int) {
        Camera.resize(width,height)
    }

    override fun render() {
        if (loading && assets.update()) {
            doneLoading()
        }

        scenes[currentScene]?.let {
            it.handleInput()
            it.render()
        }
    }

    override fun dispose() {
        scenes.map { it.value.dispose() }
        assets.dispose()
    }
}
