package net.drshoggoth.games.drshoggoth.scenes

import net.drshoggoth.games.drshoggoth.Assets
import net.drshoggoth.games.drshoggoth.SceneResponse

object LoadingScene: Scene {
    override fun create() = Assets.load()

    override fun update() = if(Assets.update()) { SceneResponse.DONE_LOADING } else { SceneResponse.NONE }

    override fun render() {}

    override fun dispose() {}
}
