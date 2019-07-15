package net.drshoggoth.games.drshoggoth.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.TimeUtils
import net.drshoggoth.games.drshoggoth.Camera
import net.drshoggoth.games.drshoggoth.GameConstants.COLORS
import net.drshoggoth.games.drshoggoth.models.Pill
import net.drshoggoth.games.drshoggoth.models.PillBottle
import net.drshoggoth.games.drshoggoth.responses.GameResponse
import net.drshoggoth.games.drshoggoth.views.PillView

object GameScene: Scene {
        private var currentPillView: PillView? = null

        var rotatedAt = 0L
        var movedAt = 0L
        var movedDownAt = 0L
        private var pillBottle = PillBottle()
        private val environment = Environment()
        val instances = Array<ModelInstance>()
        lateinit var modelBatch: ModelBatch

        fun commitIfValid(next: Pill) =
                if (pillBottle.hasRoomFor(next)) {
                        currentPillView!!.update(next)
                        true
                } else {
                        false
                }

        fun randomColor() = COLORS[MathUtils.random(0,2)]

        fun newPill(): Boolean =
                Pill(randomColor(), randomColor(), GridPoint2(pillBottle.width / 2, pillBottle.height)).let {
                        return if( pillBottle.hasRoomFor(it) ) {
                                currentPillView = PillView(it)
                                currentPillView!!.bitViews.forEach { view -> instances.add(view.model) }
                                true
                        } else { false }
                }

        override fun update(): GameResponse {
                if(currentPillView == null && !newPill() ){
                    pillBottle.empty()
                        instances.clear()
                    return GameResponse(done=true)
                }

                val pill = currentPillView!!.pill

                if(TimeUtils.timeSinceMillis(movedDownAt) > 500 ) {
                        val moved = commitIfValid(pill.moveDown())
                        if(!moved) {
                                pillBottle.addPill(currentPillView!!)
                                System.err.println( pillBottle.filledLocations())
                                currentPillView = null
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
                        return GameResponse(pause=true)
                }

                return GameResponse()
        }

        override fun render() {
                modelBatch.begin(Camera.camera)
                modelBatch.render(instances, environment)
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
                instances.clear()
        }
}