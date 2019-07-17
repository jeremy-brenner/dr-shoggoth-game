package net.drshoggoth.games.drshoggoth.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.utils.TimeUtils
import net.drshoggoth.games.drshoggoth.BitViewManager
import net.drshoggoth.games.drshoggoth.Camera
import net.drshoggoth.games.drshoggoth.SceneResponse
import net.drshoggoth.games.drshoggoth.models.Pill
import net.drshoggoth.games.drshoggoth.models.PillBottle
import net.drshoggoth.games.drshoggoth.models.PillIterator

object GameScene : Scene {

    var rotatedAt = 0L
    var movedAt = 0L
    var movedDownAt = 0L
    private var pillBottle = PillBottle()
    private val environment = Environment()
    lateinit var modelBatch: ModelBatch
    private var currentPill: Pill? = null

    fun newGame() {
        PillIterator.shuffle()
        currentPill = null
    }

    private fun newPill() = PillIterator.next().let { pill ->
        pill.bits.forEach { BitViewManager.add(it) }
        currentPill = pill
        pillBottle.hasRoomFor(currentPill!!.getPoints())
    }

    fun commitIfValid(pill: Pill) =
            if (pillBottle.hasRoomFor(pill)) {
                currentPill!!.commit(pill)
                true
            } else {
                false
            }

    override fun update(): SceneResponse {

        if (currentPill == null && !newPill()) {
            pillBottle.empty()
            BitViewManager.clear()
            return SceneResponse.GAME_OVER
        }

        val pill = currentPill!!

        if (TimeUtils.timeSinceMillis(movedDownAt) > 300) {
            val moved = commitIfValid(pill.moveDown())
            if (!moved) {
                pillBottle.addPill(currentPill!!)
                while(pillBottle.deletableBits().count() > 0){
                    pillBottle.flagBitsDeleted()
                    BitViewManager.cleanUp()
                    pillBottle.cleanUp()
                    pillBottle.movePillsDown()
                }
                currentPill = null
            }
            movedDownAt = TimeUtils.millis()
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && TimeUtils.timeSinceMillis(rotatedAt) > 200) {
            commitIfValid(pill.rotate()) || commitIfValid(pill.moveLeft().rotate())
            rotatedAt = TimeUtils.millis()
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && TimeUtils.timeSinceMillis(movedAt) > 200) {
            commitIfValid(pill.moveLeft())
            movedAt = TimeUtils.millis()
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && TimeUtils.timeSinceMillis(movedAt) > 200) {
            commitIfValid(pill.moveRight())
            movedAt = TimeUtils.millis()
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            return SceneResponse.PAUSE
        }

        BitViewManager.update()
        return SceneResponse.NONE
    }

    override fun render() {
        modelBatch.begin(Camera.camera)
        modelBatch.render(BitViewManager.instances, environment)
        modelBatch.end()
    }

    override fun create() {
        modelBatch = ModelBatch()
        environment.set(ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f))
        environment.add(DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f))
        movedDownAt = TimeUtils.millis()
    }

    override fun dispose() {
        modelBatch.dispose()
        BitViewManager.clear()
    }
}