package net.drshoggoth.games.drshoggoth.scenes

import net.drshoggoth.games.drshoggoth.Assets.assets
import net.drshoggoth.games.drshoggoth.PillLoader
import net.drshoggoth.games.drshoggoth.PillModels
import net.drshoggoth.games.drshoggoth.responses.DoneLoadingResponse

class LoadingScene: Scene {
    override fun create() {
        PillLoader.load()
    }

    override fun update(): DoneLoadingResponse {
        val done = assets.update()
        if (done) {
            PillModels.pillModels = PillLoader.get()
        }
        return DoneLoadingResponse(done)
    }

    override fun render() {}

    override fun dispose() {}
}