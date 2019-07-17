package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.graphics.g3d.ModelInstance
import net.drshoggoth.games.drshoggoth.models.Bit
import net.drshoggoth.games.drshoggoth.views.BitView

object BitViewManager {
    private val bitViews: MutableList<BitView> = mutableListOf()
    val instances = Array<ModelInstance>()

    fun add(bit: Bit) {
        val view = BitView(bit)
        bitViews.add(view)
        instances.add(view.model)
    }
    fun update() = bitViews.filter { !it.bit.deleted }.forEach { it.update() }
    fun clear() {
        instances.clear()
        bitViews.clear()
    }
    fun cleanUp() {
        cleanUpModels()
        cleanUpViews()
    }
    fun cleanUpModels() = bitViews
            .filter { it.bit.deleted }
            .forEach {
                instances.removeValue(it.model,true)
            }
    fun cleanUpViews() = bitViews.removeAll { it.bit.deleted }
}