package net.drshoggoth.games.drshoggoth

import net.drshoggoth.games.drshoggoth.scenes.BackgroundScene
import net.drshoggoth.games.drshoggoth.scenes.GameScene
import net.drshoggoth.games.drshoggoth.scenes.PauseScene
import net.drshoggoth.games.drshoggoth.scenes.TitleScene

enum class SceneResponse {
    NONE {
        override fun run() {}
    },
    DONE_LOADING {
        override fun run() {
            SceneManager.current = listOf(TitleScene)
        }
    },
    NEW_GAME {
        override fun run() {
            GameScene.newGame()
            SceneManager.current = listOf(BackgroundScene, GameScene)
        }
    },
    GAME_OVER {
        override fun run() {
            SceneManager.current = listOf(TitleScene)
        }
    },
    PAUSE {
        override fun run() {
            SceneManager.current = listOf(BackgroundScene, PauseScene)
        }
    },
    UNPAUSE {
        override fun run() {
            SceneManager.current = listOf(BackgroundScene, GameScene)
        }
    };

    abstract fun run()
}