package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance
import net.drshoggoth.games.drshoggoth.Assets.manager
import net.drshoggoth.games.drshoggoth.GameConstants.COLORS

object PillLoader {

    fun load() = COLORS.forEach { loadPillModel(it) }
    fun get() = COLORS
            .map { it to ModelInstance(getPillModel(it)) }
            .toMap()

    private fun pillFile(color: String) = "${color}_pill.g3dj"
    private fun loadPillModel(color: String) = manager.load(pillFile(color), Model::class.java)
    private fun getPillModel(color: String) = manager.get(pillFile(color), Model::class.java)

}