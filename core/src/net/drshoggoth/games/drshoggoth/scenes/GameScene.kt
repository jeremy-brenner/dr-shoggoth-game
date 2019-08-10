package net.drshoggoth.games.drshoggoth.scenes

import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.utils.TimeUtils
import net.drshoggoth.games.drshoggoth.*
import net.drshoggoth.games.drshoggoth.models.Pill
import net.drshoggoth.games.drshoggoth.models.PillBottle

object GameScene : Scene {
    private var movedDownAt = 0L
    private var pillBottle = PillBottle()
    private val environment = Environment()
    private lateinit var modelBatch: ModelBatch
    private var currentPill: Pill? = null

    fun newGame() {
        PillIterator.shuffle()
        currentPill = null
    }

    override fun update(): SceneResponse {
        if (currentPill == null && !newPill()) {
            pillBottle.empty()
            BitViewManager.clear()
            return SceneResponse.GAME_OVER
        }

        val pill = currentPill!!

        if (TimeUtils.timeSinceMillis(movedDownAt) > 300) {
            if( !commitIfValid(pill.moveDown()) ) { lockCurrentPill() }
            movedDownAt = TimeUtils.millis()
        }

        if (GameInput.space()) {
            commitIfValid(pill.rotate()) || commitIfValid(pill.moveLeft().rotate())
        }

        if (GameInput.left()) {
            commitIfValid(pill.moveLeft())
        }

        if (GameInput.right()) {
            commitIfValid(pill.moveRight())
        }

        if (GameInput.enter()) {
            return SceneResponse.PAUSE
        }

        BitViewManager.update()

        return SceneResponse.NONE
    }

    override fun render() {
        modelBatch.begin(Camera.camera)
     //   modelBatch.setShader(shaderProgram)
        modelBatch.render(BitViewManager.instances, environment)
        modelBatch.end()
    }

    override fun create() {
        val config = DefaultShader.Config()

        modelBatch = ModelBatch(DefaultShaderProvider(config))

        environment.set(ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f))
        environment.add(DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f))
        movedDownAt = TimeUtils.millis()
    }

    override fun dispose() {
        modelBatch.dispose()
        BitViewManager.clear()
    }
    private fun commitIfValid(pill: Pill) =
            if (pillBottle.hasRoomFor(pill.getPoints())) {
                currentPill!!.commit(pill)
                true
            } else {
                false
            }
    private fun newPill() = PillIterator.next().let { pill ->
        pill.bits.forEach { BitViewManager.add(it) }
        currentPill = pill
        pillBottle.hasRoomFor(currentPill!!.getPoints())
    }

    private fun lockCurrentPill() {
        pillBottle.addPill(currentPill!!)
        while (pillBottle.deletableBits().count() > 0) {
            pillBottle.flagBitsDeleted()
            BitViewManager.cleanUp()
            pillBottle.removeDeletedBits()
            pillBottle.movePillsDown()
        }
        currentPill = null
    }

}