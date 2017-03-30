

import models.RawModel;
import models.TexturedModel;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class Resources {
	
	public TerrainTexturePack texturePack;
	public TexturedModel rocks;
	public TexturedModel fern;
	public TexturedModel pine;
	public TexturedModel lamp;
	public TexturedModel stanfordBunny;
	
	public Resources(Loader loader){
		texturePack = new TerrainTexturePack(new TerrainTexture(loader.loadTexture("grassy2")), new TerrainTexture(loader.loadTexture("mud")), new TerrainTexture(loader.loadTexture("grassFlowers")), new TerrainTexture(loader.loadTexture("path")));
		
		rocks = new TexturedModel(OBJFileLoader.loadOBJ("rocks", loader), new ModelTexture(loader.loadTexture("rocks")));
		
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);
		fern = new TexturedModel(OBJFileLoader.loadOBJ("fern", loader), fernTextureAtlas);
		fern.getTexture().setHasTransparency(true);
		
		pine = new TexturedModel(OBJFileLoader.loadOBJ("pine", loader), new ModelTexture(loader.loadTexture("pine")));
		pine.getTexture().setHasTransparency(true);
		
		lamp = new TexturedModel(OBJLoader.loadObjModel("lamp", loader), new ModelTexture(loader.loadTexture("lamp")));
		lamp.getTexture().setUseFakeLighting(true);
		
		RawModel bunnyModel = OBJLoader.loadObjModel("player", loader);
		stanfordBunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("playerTexture")));
		
	}
}
