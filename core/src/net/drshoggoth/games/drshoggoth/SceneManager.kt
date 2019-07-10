package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import net.drshoggoth.games.drshoggoth.Camera.camera
import net.drshoggoth.games.drshoggoth.ModelBatcher.modelBatch
import net.drshoggoth.games.drshoggoth.responses.UpdateResponse
import net.drshoggoth.games.drshoggoth.scenes.*

object SceneManager {
    var current = "loading"

    private val scenes = mapOf(
            "loading" to LoadingScene(),
            "title" to TitleScene(),
            "game" to GameScene(),
            "background" to BackgroundScene(),
            "scoreboard" to ScoreboardScene()
    )

    fun create() {
        scenes.map { it.value.create() }
    }

    fun update(): UpdateResponse? {
        return scenes[current]?.let { it.update() }
    }

    fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        modelBatch.begin(camera)
        scenes[current]?.let { it.render() }
        modelBatch.end()
    }

    fun dispose() {
        scenes.map { it.value.dispose() }
    }

}