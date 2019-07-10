package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import net.drshoggoth.games.drshoggoth.GameConstants.VIEWPORT_HEIGHT

object Camera {
    var camera = OrthographicCamera(VIEWPORT_HEIGHT * Gdx.graphics.width.toFloat() / Gdx.graphics.height.toFloat(), VIEWPORT_HEIGHT  )

    init {
        resetPosition()
        camera.update()
    }

    fun resize(width: Int, height: Int) {
        camera.viewportHeight = VIEWPORT_HEIGHT
        camera.viewportWidth = VIEWPORT_HEIGHT * width / height
        resetPosition()
        camera.update()
    }

    private fun resetPosition() {
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 5f)
    }
}