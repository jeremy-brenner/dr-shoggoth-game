package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.ApplicationAdapter
import net.drshoggoth.games.drshoggoth.Assets.assets
import net.drshoggoth.games.drshoggoth.responses.DoneLoadingResponse
import net.drshoggoth.games.drshoggoth.responses.UpdateResponse


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
        SceneManager.render()
    }

    override fun dispose() {
        SceneManager.dispose()
        assets.dispose()
    }
}
