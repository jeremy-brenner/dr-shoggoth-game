package net.drshoggoth.games.drshoggoth

import net.drshoggoth.games.drshoggoth.scenes.BackgroundScene
import net.drshoggoth.games.drshoggoth.scenes.GameScene
import net.drshoggoth.games.drshoggoth.scenes.PauseScene
import net.drshoggoth.games.drshoggoth.scenes.TitleScene

enum class SceneResponse {
    NONE {
        override fun execResponse() {}
    },
    DONE_LOADING {
        override fun execResponse() {
            SceneManager.current = listOf(TitleScene)
        }
    },
    NEW_GAME {
        override fun execResponse() {
            GameScene.newGame()
            SceneManager.current = listOf(BackgroundScene, GameScene)
        }
    },
    GAME_OVER {
        override fun execResponse() {
            SceneManager.current = listOf(TitleScene)
        }
    },
    PAUSE {
        override fun execResponse() {
            SceneManager.current = listOf(BackgroundScene, PauseScene)
        }
    },
    UNPAUSE {
        override fun execResponse() {
            SceneManager.current = listOf(BackgroundScene, GameScene)
        }
    };

    abstract fun execResponse()
}