package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import net.drshoggoth.games.drshoggoth.Assets.assets
import net.drshoggoth.games.drshoggoth.responses.DoneLoadingResponse


class DrShoggothGame : ApplicationAdapter() {
    override fun create() {
        SceneManager.create()
    }

    override fun resize(width: Int, height: Int) {
        Camera.resize(width,height)
    }

    override fun render() {
        when(val response = SceneManager.update()){
            is DoneLoadingResponse -> if(response.done) { SceneManager.current = "game" }
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        ModelBatcher.modelBatch.begin(Camera.camera)
        SceneManager.render()
        ModelBatcher.modelBatch.end()

    }

    override fun dispose() {
        SceneManager.dispose()
        assets.dispose()
    }
}
