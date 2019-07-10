package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.TimeUtils
import net.drshoggoth.games.drshoggoth.Camera.camera
import net.drshoggoth.games.drshoggoth.GameConstants.COLORS

class GameScene: Scene {
        private var currentPillView: PillView? = null

        var rotatedAt = 0L
        var movedAt = 0L
        var movedDownAt = 0L
        private var pillBottle = PillBottle()
        private val environment = Environment()
        private lateinit var modelBatch: ModelBatch
        val instances = Array<ModelInstance>()

        fun commitIfValid(next: Pill) =
                if (pillBottle.hasRoomFor(next)) {
                        currentPillView!!.update(next)
                        true
                } else {
                        false
                }

        fun randomColor() = COLORS[MathUtils.random(0,2)]

        fun newPill() {
                val pill = Pill(randomColor(), randomColor(), GridPoint2(pillBottle.width/2, pillBottle.height))
                currentPillView = PillView(pill)
                currentPillView!!.bitViews.forEach { instances.add(it.model) }
        }

        override fun handleInput() {
                if(currentPillView == null) {
                        newPill()
                }

                val pill = currentPillView!!.pill

                if(TimeUtils.timeSinceMillis(movedDownAt) > 500 ) {
                        val moved = commitIfValid(pill.moveDown())
                        if(!moved) {
                                pillBottle.addPill(currentPillView!!)
                                newPill()
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
        }

        override fun render() {
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

                modelBatch.begin(camera)
                modelBatch.render(instances, environment)
                modelBatch.end()
        }

        override fun create() {
                PillLoader.load()
        }

        override fun doneLoading() {
                PillModels.pillModels = PillLoader.get()

                environment.set(ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f))
                environment.add(DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f))

                modelBatch = ModelBatch()

                movedDownAt = TimeUtils.millis()
        }

        override fun dispose() {
                modelBatch.dispose()
                instances.clear()
        }
}