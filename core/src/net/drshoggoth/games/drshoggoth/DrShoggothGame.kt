package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20

class DrShoggothGame : ApplicationAdapter() {
    override fun create() {
        Assets.load()
        SceneManager.create()
    }

    override fun resize(width: Int, height: Int) {
        Camera.resize(width, height)
    }

    override fun render() {
        SceneManager.update().forEach { it.response() }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        SceneManager.render()
    }

    override fun dispose() {
        SceneManager.dispose()
        Assets.dispose()
    }
}
