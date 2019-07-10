package net.drshoggoth.games.drshoggoth.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import net.drshoggoth.games.drshoggoth.DrShoggothGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        LwjglApplication(DrShoggothGame(), config)
    }
}
