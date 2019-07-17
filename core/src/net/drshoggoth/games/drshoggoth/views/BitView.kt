package net.drshoggoth.games.drshoggoth.views

import com.badlogic.gdx.math.Vector3
import net.drshoggoth.games.drshoggoth.Assets
import net.drshoggoth.games.drshoggoth.models.Bit

class BitView(var bit: Bit) {
    var model = Assets.getPillModelInstance(bit.color)

    init {
        update()
    }

    fun update() {
        model.transform.setToTranslation(bit.location.x + 0.5f, bit.location.y + 0.5f, 0f)
        if(bit.single) {
            model.transform.rotate(Vector3(1f, 0f, 0f), -90f)
        }else {
            model.transform.rotate(Vector3(0f, 0f, 1f), bit.rotation * -90f)
        }
    }

}



