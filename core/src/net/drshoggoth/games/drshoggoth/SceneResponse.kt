package net.drshoggoth.games.drshoggoth

import net.drshoggoth.games.drshoggoth.scenes.BackgroundScene
import net.drshoggoth.games.drshoggoth.scenes.GameScene
import net.drshoggoth.games.drshoggoth.scenes.PauseScene
import net.drshoggoth.games.drshoggoth.scenes.TitleScene

enum class SceneResponse(val response: () -> Unit) {
    NONE({}),
    DONE_LOADING({
        SceneManager.current = listOf(TitleScene)
    }),
    NEW_GAME({
        GameScene.newGame()
        SceneManager.current = listOf(BackgroundScene, GameScene)
    }),
    GAME_OVER({
        SceneManager.current = listOf(TitleScene)
    }),
    PAUSE({
        SceneManager.current = listOf(BackgroundScene, PauseScene)
    }),
    UNPAUSE({
        SceneManager.current = listOf(BackgroundScene, GameScene)
    });
}