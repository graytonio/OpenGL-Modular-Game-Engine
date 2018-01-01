import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import engine.BaseEngine;
import entities.Camera;
import entities.Entity;
import entities.ExamplePlayer;
import entities.Light;
import entities.Player;
import entities.ThirdPersonCamera;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import terrains.Terrain;
import textures.TerrainTexture;
import water.WaterTile;

public class MainGameLoop {
	
	static Player player;
	static BaseEngine engine;

	public static void main(String[] args) {

		//**********Engine Setup***************
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		Resources res=new Resources(loader);
		
		Light sun = new Light(new Vector3f(10000, 10000, -10000), new Vector3f(1.3f, 1.3f, 1.3f));
		Player player = new ExamplePlayer(res.stanfordBunny, new Vector3f(75, 5, 75), 0.6f, 5f, 10f, 5f);
		Camera camera = new ThirdPersonCamera(player);

		engine=new BaseEngine(loader, player, camera, sun, 1000f);
		GUIEngine gui=new GUIEngine(engine, loader);

		// *********TERRAIN STUFF**********
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		Terrain terrain = new Terrain(0, 0, loader, res.texturePack, blendMap, "heightMap", 150);
		engine.addTerrain(terrain);
		
		engine.setSkyboxSize(600f);
		
		engine.addWater(new WaterTile(75, 75, 0));
		engine.addEntity(new Entity(res.rocks, new Vector3f(75, 4.6f, 75), 0, 0, 0, 75));
		engine.addEntity(new Entity(res.pine, new Vector3f(100, 5, 100), 1, 5, 50, 5));
		
		gui.addGUIImage("gui/health", -0.8f, 0.9f, 0.2f);
		
		while (!Display.isCloseRequested()) {
			engine.update();
		}
		
		engine.cleanUp();
	}

}
