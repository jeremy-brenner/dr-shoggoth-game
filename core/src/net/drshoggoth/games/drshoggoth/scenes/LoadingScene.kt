package net.drshoggoth.games.drshoggoth.scenes

import net.drshoggoth.games.drshoggoth.Assets.manager
import net.drshoggoth.games.drshoggoth.PillLoader
import net.drshoggoth.games.drshoggoth.PillModels
import net.drshoggoth.games.drshoggoth.responses.DoneLoadingResponse

class LoadingScene: Scene {
    override fun create() {
        PillLoader.load()
    }

    override fun update(): DoneLoadingResponse {
        val done = manager.update()
        if (done) {
            PillModels.pillModels = PillLoader.get()
        }
        return DoneLoadingResponse(done)
    }

    override fun render() {}

    override fun dispose() {}
}