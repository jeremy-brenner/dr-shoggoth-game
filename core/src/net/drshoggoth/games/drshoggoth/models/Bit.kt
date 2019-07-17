package net.drshoggoth.games.drshoggoth.models

import com.badlogic.gdx.math.GridPoint2

data class Bit (
            val color: String,
            val location: GridPoint2,
            var rotation: Int,
            var single: Boolean = false,
            var deleted: Boolean = false
) {
    fun nextTo(other: Bit) = other.location.x - location.x in -1..1 && other.location.y - location.y in -1..1
    fun moveDown() = Bit(color,location.cpy().sub(0,1),rotation,single)
}