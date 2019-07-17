package net.drshoggoth.games.drshoggoth.scenes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import net.drshoggoth.games.drshoggoth.Assets
import net.drshoggoth.games.drshoggoth.GameInput
import net.drshoggoth.games.drshoggoth.SceneResponse

object PauseScene: Scene {
    lateinit var spriteBatch: SpriteBatch

    override fun update() = if (GameInput.enter()) { SceneResponse.UNPAUSE } else { SceneResponse.NONE }
    override fun render() {
        spriteBatch.begin()
        Assets.getFont().draw(spriteBatch, "PAUSED", 100f, 300f)
        spriteBatch.end()
    }
    override fun create() {
        spriteBatch = SpriteBatch()
    }
    override fun dispose() {}
}

