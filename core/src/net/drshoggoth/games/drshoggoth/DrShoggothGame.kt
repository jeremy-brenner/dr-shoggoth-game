package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.ApplicationAdapter
import net.drshoggoth.games.drshoggoth.Assets.assets


class DrShoggothGame : ApplicationAdapter() {
    private var loading: Boolean = true
    private var sceneManager = SceneManager()

    override fun create() {
        sceneManager.create()
    }

    private fun doneLoading() {
        sceneManager.doneLoading()
        sceneManager.current = "game"
        loading = false
    }

    override fun resize(width: Int, height: Int) {
        Camera.resize(width,height)
    }

    override fun render() {
        if (loading && assets.update()) {
            doneLoading()
        }
        sceneManager.render()
    }

    override fun dispose() {
        sceneManager.dispose()
        assets.dispose()
    }
}
