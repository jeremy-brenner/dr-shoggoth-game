package net.drshoggoth.games.drshoggoth.views

import net.drshoggoth.games.drshoggoth.models.Pill

class PillView(var pill: Pill){
    val bitViews = pill.bits.map { PillBitView(it) }

    init {
        update()
    }

    fun update(next: Pill) {
        pill = next
        pill.bits.forEachIndexed { i, bit -> bitViews[i].bit = bit }
        update()
    }

    fun update() {
        bitViews.forEach { it.update() }
    }
}