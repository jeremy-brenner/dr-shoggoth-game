package net.drshoggoth.games.drshoggoth.models

data class Pill(
        var bits: MutableList<Bit>
) {
    fun moveDown() = Pill(bits.map { it.moveDown() }.toMutableList())
    fun moveLeft() =  Pill(bits.map { Bit(it.color,it.location.cpy().sub(1,0),it.rotation, it.single) }.toMutableList())
    fun moveRight() = Pill(bits.map { Bit(it.color,it.location.cpy().add(1,0),it.rotation, it.single) }.toMutableList())
    fun rotate() = Pill(bits.map { Bit(it.color,it.location.cpy().add(nextY(it.rotation),nextX(it.rotation)), nextRotation(it.rotation),it.single) }.toMutableList())
    private fun nextRotation(rotation: Int) = (rotation+1)%4
    private fun nextX(rotation: Int) = when(nextRotation(rotation)) {
        2 -> 1
        3 -> -1
        else -> 0
    }
    private fun nextY(rotation: Int) = when(nextRotation(rotation)) {
        0 -> -1
        3 -> 1
        else -> 0
    }
    fun commit(pill: Pill) = pill.bits.forEachIndexed { i,bit ->
        bits[i].rotation = bit.rotation
        bits[i].location.set(bit.location)
    }
    fun getPoints() = bits.map { it.location }
    fun cleanUp() {
        bits.removeAll { it.deleted }
        if(bits.count() == 1) {
            bits[0].single = true
        }
    }
}