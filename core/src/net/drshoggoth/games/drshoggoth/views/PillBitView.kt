package net.drshoggoth.games.drshoggoth.views

import com.badlogic.gdx.math.Vector3
import net.drshoggoth.games.drshoggoth.Assets.getPillModelInstance
import net.drshoggoth.games.drshoggoth.models.PillBit

class PillBitView(var bit: PillBit){
    var model = getPillModelInstance(bit.color)

    init {
        update()
    }

    fun update() {
        model.transform.setToTranslation(bit.location.x + 0.5f, bit.location.y + 0.5f, 0f)
        model.transform.rotate(Vector3(0f, 0f, 1f), bit.angle)
    }

}



