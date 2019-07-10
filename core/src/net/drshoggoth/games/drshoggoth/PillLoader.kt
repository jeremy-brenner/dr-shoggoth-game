package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance
import net.drshoggoth.games.drshoggoth.Assets.assets
import net.drshoggoth.games.drshoggoth.GameConstants.COLORS

object PillLoader {

    fun load() = COLORS.forEach { loadPillModel(it) }
    fun get() = COLORS
            .map { it to ModelInstance(getPillModel(it)) }
            .toMap()

    private fun pillFile(color: String) = "${color}_pill.g3dj"
    private fun loadPillModel(color: String) = assets.load(pillFile(color), Model::class.java)
    private fun getPillModel(color: String) = assets.get(pillFile(color), Model::class.java)

}