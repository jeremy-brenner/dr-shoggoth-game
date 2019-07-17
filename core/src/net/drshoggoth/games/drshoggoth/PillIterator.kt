package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.math.GridPoint2
import net.drshoggoth.games.drshoggoth.GameConstants.BOTTLE_HEIGHT
import net.drshoggoth.games.drshoggoth.GameConstants.BOTTLE_WIDTH
import net.drshoggoth.games.drshoggoth.GameConstants.COLORS
import net.drshoggoth.games.drshoggoth.GameConstants.ITERATIONS
import net.drshoggoth.games.drshoggoth.models.Bit
import net.drshoggoth.games.drshoggoth.models.Pill

object PillIterator {
    private val combinations = COLORS.flatMap { c1 -> COLORS.map { c2 -> listOf(c1,c2) } }
    private val sequenceLength = combinations.count()*ITERATIONS
    private val pillSequence: MutableList<List<String>> = (0 until sequenceLength).map { combinations[it % combinations.count()] }.toMutableList()
    private var iterator: Iterator<List<String>> = pillSequence.iterator()
    private var origin = GridPoint2(BOTTLE_WIDTH / 2, BOTTLE_HEIGHT)

    fun next(): Pill {
        if (!iterator.hasNext()) {
            resetIterator()
        }
        return nextPill(iterator.next())
    }

    fun shuffle() {
        pillSequence.shuffle()
        resetIterator()
    }

    private fun resetIterator() {
        iterator = pillSequence.iterator()
    }

    private fun nextPill(colors: List<String>) = Pill(mutableListOf(Bit(colors[0], origin.cpy(), 0), Bit(colors[1], origin.cpy().add(0, 1), 2)))
}