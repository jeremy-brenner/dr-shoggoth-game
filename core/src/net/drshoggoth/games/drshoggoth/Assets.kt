package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance

object Assets {
    val manager = AssetManager()
    private val resolver = InternalFileHandleResolver()

    fun load() {
        loadPills()
        loadFonts()
    }

    fun dispose() = manager.dispose()

    fun getPillModelInstance(color: String) = ModelInstance(manager.get(pillFile(color), Model::class.java))
    fun getFont() = Assets.manager.get("fonts/OpenSans-Bold.ttf", BitmapFont::class.java)

    private fun pillFile(color: String) = "${color}_pill.g3dj"
    private fun loadPillModel(color: String) = manager.load(pillFile(color), Model::class.java)
    private fun loadPills() = GameConstants.COLORS.forEach { loadPillModel(it) }
    private fun loadFonts() {
        manager.setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolver))
        manager.setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolver))
        val myBigFont = FreetypeFontLoader.FreeTypeFontLoaderParameter()
        myBigFont.fontFileName = "fonts/OpenSans-Bold.ttf"
        myBigFont.fontParameters.size = 20
        Assets.manager.load("fonts/OpenSans-Bold.ttf", BitmapFont::class.java, myBigFont)
    }
}