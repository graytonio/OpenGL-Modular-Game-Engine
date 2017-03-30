import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import engine.BaseEngine;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import guis.GuiTexture;
import renderEngine.Loader;

public class GUIEngine {
	
	private BaseEngine engine;
	private Loader loader;
	
	private List<FontType> fonts = new ArrayList<FontType>();
	
	public GUIEngine(BaseEngine engine, Loader loader){
		
		this.engine=engine;
		this.loader=loader;
		
		fonts.add(new FontType(loader.loadFontTextureAtlas("arial"), new File("res/fonts/arial.fnt")));
		fonts.add(new FontType(loader.loadFontTextureAtlas("berlinSans"), new File("res/fonts/berlinSans.fnt")));
		fonts.add(new FontType(loader.loadFontTextureAtlas("calibri"), new File("res/fonts/calibri.fnt")));
		fonts.add(new FontType(loader.loadFontTextureAtlas("candara"), new File("res/fonts/candara.fnt")));
		fonts.add(new FontType(loader.loadFontTextureAtlas("harrington"), new File("res/fonts/harrington.fnt")));
		fonts.add(new FontType(loader.loadFontTextureAtlas("sans"), new File("res/fonts/sans.fnt")));
		fonts.add(new FontType(loader.loadFontTextureAtlas("segoe"), new File("res/fonts/segoe.fnt")));
		fonts.add(new FontType(loader.loadFontTextureAtlas("segoeUI"), new File("res/fonts/segoeUI.fnt")));
		fonts.add(new FontType(loader.loadFontTextureAtlas("tahoma"), new File("res/fonts/tahoma.fnt")));
	}
	
	public void addText(String text, String font, float size, float x, float y, float maxLineLength, boolean centered){
		new GUIText(text, size, getFont(font), new Vector2f(x, y), maxLineLength, centered);
	}
	
	public void addGUIImage(String texture, float x, float y, float scale){
		engine.addGUIImage(new GuiTexture(loader.loadTexture(texture), new Vector2f(x, y), new Vector2f(scale, scale)));
	}
	
	public void addGUIImage(GuiTexture tex){
		engine.addGUIImage(tex);
	}
	
	private FontType getFont(String name){
		for(FontType font:fonts){
			if(font.equals(name)){
				return font;
			}
		}
		return fonts.get(0);
	}
}
