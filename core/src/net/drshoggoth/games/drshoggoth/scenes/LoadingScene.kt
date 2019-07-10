package net.drshoggoth.games.drshoggoth.scenes

import net.drshoggoth.games.drshoggoth.Assets
import net.drshoggoth.games.drshoggoth.Assets.manager
import net.drshoggoth.games.drshoggoth.responses.DoneLoadingResponse

class LoadingScene: Scene {
    override fun create() = Assets.load()

    override fun update() = DoneLoadingResponse(manager.update())

    override fun render() {}

    override fun dispose() {}
}