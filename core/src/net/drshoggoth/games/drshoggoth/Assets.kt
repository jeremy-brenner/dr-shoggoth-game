package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance

object Assets {
    private val manager = AssetManager()
    private val resolver = InternalFileHandleResolver()
    private val fontName = "OpenSans-Bold"
    private val fontFile = "fonts/${fontName}.ttf"
    private val fontSize = 30

    fun load() {
        loadPills()
        loadFonts()
    }

    fun dispose() = manager.dispose()
    fun update() = manager.update()

    fun getModelInstance(fileName: String) = ModelInstance(manager.get(fileName, Model::class.java))

    fun getPillModelInstance(color: String) = getModelInstance(pillFile(color))
    fun getFont() = Assets.manager.get("fonts/OpenSans-Bold.ttf", BitmapFont::class.java)

    private fun loadModel(fileName: String) = manager.load(fileName, Model::class.java)
    private fun pillFile(color: String) = "${color}_pill.g3dj"
    private fun loadPillModel(color: String) = loadModel(pillFile(color))
    private fun loadPills() = GameConstants.COLORS.forEach { loadPillModel(it) }
    private fun loadFonts() {
        manager.setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolver))
        manager.setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolver))
        Assets.manager.load(fontFile, BitmapFont::class.java, fontParameters())
    }
    private fun fontParameters() = FreetypeFontLoader.FreeTypeFontLoaderParameter().also {
        it.fontFileName = fontFile
        it.fontParameters.size = fontSize
        it.fontParameters.borderColor = Color.BLUE;
        it.fontParameters.borderWidth = 3f;
    }
}