package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import net.drshoggoth.games.drshoggoth.responses.DoneLoadingResponse
import net.drshoggoth.games.drshoggoth.responses.MenuSelectionResponse


class DrShoggothGame : ApplicationAdapter() {
    override fun create() {
        Assets.load()
        SceneManager.create()
    }

    override fun resize(width: Int, height: Int) {
        Camera.resize(width,height)
    }

    override fun render() {
        when(val response = SceneManager.update()){
            is DoneLoadingResponse -> handleDoneLoading(response)
            is MenuSelectionResponse -> handleMenuSelection(response)
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        SceneManager.render()
    }

    fun handleDoneLoading(response: DoneLoadingResponse) {
        if(response.done) { SceneManager.current = "title" }
    }

    fun handleMenuSelection(response: MenuSelectionResponse) {
        SceneManager.current = response.selection
    }

    override fun dispose() {
        SceneManager.dispose()
        Assets.dispose()
    }
}
