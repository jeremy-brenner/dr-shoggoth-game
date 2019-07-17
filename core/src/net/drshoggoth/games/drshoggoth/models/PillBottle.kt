package net.drshoggoth.games.drshoggoth.models

import com.badlogic.gdx.math.GridPoint2
import net.drshoggoth.games.drshoggoth.GameConstants.BOTTLE_HEIGHT
import net.drshoggoth.games.drshoggoth.GameConstants.BOTTLE_WIDTH

data class PillBottle(
        val width: Int = BOTTLE_WIDTH,
        val height: Int = BOTTLE_HEIGHT
) {
    private val pills: MutableList<Pill> = mutableListOf()
    fun addPill(pill: Pill) = pills.add(pill)
    private fun bits() = pills.flatMap { it.bits }
    fun hasRoomFor(pill: Pill) = hasRoomFor(pill.getPoints())
    fun hasRoomFor(points: List<GridPoint2>) = points.all { hasRoomFor(it) }
    private fun hasRoomFor(point: GridPoint2) =
            point.x in 0 until width && point.y >= 0 && !filledLocations().contains(point)

    private fun filledLocations() = bits().map { it.location }
    private fun rows() = bits().sortedBy { it.location.x }.groupBy { it.location.y }.values
    private fun cols() = bits().sortedBy { it.location.y }.groupBy { it.location.x }.values
    private fun sets() = rows().plus(cols())
    private fun groups() = sets().flatMap { groupSet(it) }
    private fun groupSet(set: List<Bit>): MutableList<MutableList<Bit>> {
        val matches: MutableList<MutableList<Bit>> = mutableListOf()
        set.forEach {
            if (matches.count() > 0 &&
                    matches.last().last().color == it.color &&
                    matches.last().last().nextTo(it)) {
                matches.last().add(it)
            } else {
                matches.add(mutableListOf())
                matches.last().add(it)
            }
        }
        return matches
    }

    fun deletableBits() = groups().filter { it.count() > 3 }.flatten()
    fun flagBitsDeleted() = deletableBits().forEach { it.deleted = true }
    fun cleanUp() {
        pills.filter { pill -> pill.bits.any { bit -> bit.deleted } }.forEach { it.cleanUp() }
        pills.removeAll { it.bits.count() == 0 }
    }
    fun empty() = pills.clear()
    fun movePillsDown() {
        var movedSomething: Boolean
        do {
            movedSomething = false
            pills.sortedBy { it.bits.minBy { bit -> bit.location.y }?.location?.y }.forEach {
                val pill = it.moveDown()
                if (hasRoomFor(pill)) {
                    it.commit(pill)
                    movedSomething = true
                }
            }
        } while (movedSomething)
    }
}