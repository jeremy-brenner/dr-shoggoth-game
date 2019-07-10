package net.drshoggoth.games.drshoggoth.models

import com.badlogic.gdx.math.GridPoint2

data class PillBit (
            val color: String,
            val location: GridPoint2,
            var angle: Float
    )