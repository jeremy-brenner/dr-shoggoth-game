package net.drshoggoth.games.drshoggoth.models

import com.badlogic.gdx.math.GridPoint2

data class Pill(
        val color1: String,
        val color2: String,
        var origin: GridPoint2,
        var rotation: Int = 0
) {
    var bits: List<PillBit> = listOf(
            PillBit(color1, GridPoint2(origin.x + modX(rotation), origin.y + modY(rotation)), rotation * -90f),
            PillBit(color2, GridPoint2(origin.x + modX(rotation + 2), origin.y + modY(rotation + 2)), (rotation + 2) * -90f)
    )

    fun rotate() = Pill(color1, color2, origin.cpy(), (rotation + 1) % 4)
    fun moveDown() = Pill(color1, color2, GridPoint2(origin.x, origin.y - 1), rotation)
    fun moveLeft() = Pill(color1, color2, GridPoint2(origin.x - 1, origin.y), rotation)
    fun moveRight() = Pill(color1, color2, GridPoint2(origin.x + 1, origin.y), rotation)

    fun getPoints() = bits.map { it.location }

    private fun modX(r: Int) = if (r == 3) 1 else 0
    private fun modY(r: Int) = if (r == 2) 1 else 0

}