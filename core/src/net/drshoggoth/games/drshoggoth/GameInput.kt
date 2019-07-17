package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

object GameInput {
    fun enter() = Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
    fun space() = Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
    fun left() = Gdx.input.isKeyJustPressed(Input.Keys.LEFT)
    fun right() = Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)
}