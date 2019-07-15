package net.drshoggoth.games.drshoggoth.scenes

import net.drshoggoth.games.drshoggoth.Assets
import net.drshoggoth.games.drshoggoth.responses.DoneLoadingResponse

object LoadingScene: Scene {
    override fun create() = Assets.load()

    override fun update() = DoneLoadingResponse(Assets.update())

    override fun render() {}

    override fun dispose() {}
}
