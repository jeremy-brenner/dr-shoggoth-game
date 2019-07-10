package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.math.Vector3
import net.drshoggoth.games.drshoggoth.PillModels.pillModels

class PillBitView(var bit: PillBit){
    var model = pillModels.getValue(bit.color).copy()

    init {
        update()
    }

    fun update() {
        model.transform?.setToTranslation(bit.location.x + 0.5f, bit.location.y + 0.5f, 0f)
        model.transform?.rotate(Vector3(0f, 0f, 1f), bit.angle)
    }

}



