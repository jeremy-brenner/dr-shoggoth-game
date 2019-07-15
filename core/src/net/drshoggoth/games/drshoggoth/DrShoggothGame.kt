package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import net.drshoggoth.games.drshoggoth.responses.DoneLoadingResponse
import net.drshoggoth.games.drshoggoth.responses.GameResponse
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
        SceneManager.update().forEach { response ->
          when(response){
            is DoneLoadingResponse -> handleDoneLoading(response)
            is MenuSelectionResponse -> handleMenuSelection(response)
            is GameResponse -> handleGameResponse(response)
          }
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        SceneManager.render()
    }

    fun handleDoneLoading(response: DoneLoadingResponse) {
        if(response.done) { SceneManager.current = listOf("title") }
    }

    fun handleMenuSelection(response: MenuSelectionResponse) {
        SceneManager.current = listOf(response.selection)
    }

    fun handleGameResponse(response: GameResponse) {
        if(response.done) { SceneManager.current = listOf("title") }
    }

    override fun dispose() {
        SceneManager.dispose()
        Assets.dispose()
    }
}
