package net.drshoggoth.games.drshoggoth.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import net.drshoggoth.games.drshoggoth.FontLoader
import net.drshoggoth.games.drshoggoth.responses.MenuSelectionResponse

class TitleScene: Scene {
    lateinit var spriteBatch: SpriteBatch

    override fun update(): MenuSelectionResponse? {
        return if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            MenuSelectionResponse("game")
        } else {
            null
        }
    }

    override fun render() {
        spriteBatch.begin()
        FontLoader.get().draw(spriteBatch, "Hello World!", 100f, 100f)
        spriteBatch.end()
    }

    override fun create() {
        spriteBatch = SpriteBatch()
    }

    override fun dispose() {}
}