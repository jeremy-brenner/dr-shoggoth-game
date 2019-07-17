package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import net.drshoggoth.games.drshoggoth.scenes.GameScene
import net.drshoggoth.games.drshoggoth.scenes.TitleScene

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
            SceneResponse.DONE_LOADING -> handleDoneLoading()
            SceneResponse.NEW_GAME -> handleNewGame()
            SceneResponse.GAME_OVER -> handleGameOver()
          }
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        SceneManager.render()
    }

    fun handleDoneLoading() {
         SceneManager.current = listOf(TitleScene)
    }

    fun handleNewGame() {
        GameScene.newGame()
        SceneManager.current = listOf(GameScene)
    }

    fun handleGameOver() {
        SceneManager.current = listOf(TitleScene)
    }

    override fun dispose() {
        SceneManager.dispose()
        Assets.dispose()
    }
}
