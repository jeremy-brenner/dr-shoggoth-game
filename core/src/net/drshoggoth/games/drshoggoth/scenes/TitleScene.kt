package net.drshoggoth.games.drshoggoth.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import net.drshoggoth.games.drshoggoth.Assets
import net.drshoggoth.games.drshoggoth.SceneResponse

object TitleScene : Scene {
    lateinit var spriteBatch: SpriteBatch

    override fun update() = if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
        SceneResponse.NEW_GAME
    } else {
        SceneResponse.NONE
    }

    override fun render() {
        spriteBatch.begin()
        Assets.getFont().draw(spriteBatch, "Hello World!", 100f, 100f)
        spriteBatch.end()
    }

    override fun create() {
        spriteBatch = SpriteBatch()
    }

    override fun dispose() {}
}