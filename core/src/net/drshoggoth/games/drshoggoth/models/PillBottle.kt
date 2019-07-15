package net.drshoggoth.games.drshoggoth.models

import com.badlogic.gdx.math.GridPoint2
import net.drshoggoth.games.drshoggoth.views.PillView

data class PillBottle(
        val width: Int = 8,
        val height: Int = 16
) {

    val pillViews: MutableList<PillView> = mutableListOf()

    fun hasRoomFor(pill: Pill) =
            pill.getPoints().all { hasRoomFor(it) }

    fun hasRoomFor(point: GridPoint2) =
            point.x in 0 until width && point.y >= 0 && !filledLocations().contains(point)

    fun addPill(pillView: PillView) = pillViews.add(pillView)

    fun filledLocations() = pillViews.flatMap { it.pill.getPoints() }

    fun empty() = pillViews.clear()
}