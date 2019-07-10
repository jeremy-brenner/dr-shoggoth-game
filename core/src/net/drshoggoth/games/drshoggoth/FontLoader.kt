package net.drshoggoth.games.drshoggoth

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter




object FontLoader {
    fun load() {
        val resolver = InternalFileHandleResolver()
        Assets.manager.setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolver))
        Assets.manager.setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolver))
        val myBigFont = FreeTypeFontLoaderParameter()
        myBigFont.fontFileName = "fonts/OpenSans-Bold.ttf"
        myBigFont.fontParameters.size = 20
        Assets.manager.load("fonts/OpenSans-Bold.ttf", BitmapFont::class.java, myBigFont)
    }

    fun get(): BitmapFont {
        return Assets.manager.get("fonts/OpenSans-Bold.ttf", BitmapFont::class.java)
    }

  //  private fun getPillModel(color: String) = Assets.manager.get(pillFile(color), Model::class.java)

}
/*
		FreeTypeFontLoaderParameter size2Params = new FreeTypeFontLoaderParameter();
		size2Params.fontFileName = "data/arial.ttf";
		size2Params.fontParameters.size = 20;
		manager.load("size20.ttf", BitmapFont.class, size2Params);

 */