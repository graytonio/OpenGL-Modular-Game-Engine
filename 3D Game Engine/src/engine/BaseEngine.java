package engine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import particles.ParticleMaster;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class BaseEngine {

	public List<Terrain> terrains = new ArrayList<Terrain>();
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Entity> normalMapEntities = new ArrayList<Entity>();
	public List<Light> lights = new ArrayList<Light>();
	public List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
	public List<WaterTile> waters = new ArrayList<WaterTile>();

	Player player;
	Camera camera;
	Light sun;

	MasterRenderer renderer;
	GuiRenderer guiRenderer;
	WaterFrameBuffers buffers;
	WaterShader waterShader;
	WaterRenderer waterRenderer;

	Loader loader;

	/**
	 * @param loader Loader Object
	 * @param player Player Object to Focus On
	 * @param sun Main Light Object
	 */
	public BaseEngine(Loader loader, Player player, Camera camera, Light sun, float skyboxSize){
		this.loader=loader;
		this.player=player;
		this.sun=sun;
		this.camera=camera;
		
		this.addEntity(player);
		this.lights.add(sun);
		
		TextMaster.init(loader);
		this.renderer = new MasterRenderer(loader, skyboxSize, camera);
		this.guiRenderer = new GuiRenderer(loader);
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		this.buffers = new WaterFrameBuffers();
		this.waterShader = new WaterShader();
		this.waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
	}

	private Terrain getTerrain(){
		int gridX = (int) (player.getPosition().x / Terrain.getSize());
		int gridZ = (int) (player.getPosition().z / Terrain.getSize());
		for(Terrain ter:terrains){
			if(ter.getX()/Terrain.getSize()==gridX && ter.getZ()/Terrain.getSize()==gridZ){
				return ter;
			}
		}
		return null;
	}

	/**
	 * Update the Rendering Engine. Must be called for every frame
	 * @throws Exception 
	 */
	public void update() {
		//	Timer.tick();
		if(player.getColBox()!=null){
			player.move(getTerrain(), entities);
		}else{
			player.move(getTerrain());
		}
		camera.move();

		ParticleMaster.update(camera);
		renderer.renderShadowMap(entities, sun);
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

		for(WaterTile water:waters){
			prepWater(water);
		}

		// render to screen
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		buffers.unbindCurrentFrameBuffer();
		renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 100000));
		waterRenderer.render(waters, camera, sun);
		ParticleMaster.renderParticles(camera);
		guiRenderer.render(guiTextures);
		TextMaster.render();

		DisplayManager.updateDisplay();
	}

	private void prepWater(WaterTile water){
		// render reflection teture
		buffers.bindReflectionFrameBuffer();
		float distance = 2 * (camera.getPosition().y - water.getHeight());
		camera.getPosition().y -= distance;
		camera.invertPitch();
		renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, -water.getHeight() + 1));
		camera.getPosition().y += distance;
		camera.invertPitch();

		// render refraction texture
		buffers.bindRefractionFrameBuffer();
		renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, water.getHeight()));
	}

	/**
	 * Clean up the Render Engine.  Must be called to properly close game
	 */
	public void cleanUp(){
		ParticleMaster.cleanUp();
		TextMaster.cleanUp();
		buffers.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

	/**
	 * Add Terrain Object to Render Engine
	 * @param ter Terrain Object
	 */
	public void addTerrain(Terrain ter){
		terrains.add(ter);
	}

	/**
	 * Add Entity to Render Engine
	 * @param ent Entity
	 */
	public void addEntity(Entity ent){
		entities.add(ent);
		System.out.println("Added: Entity " + entities.size());
	}

	/**
	 * Add Normal Mapped Entity to Render Engine
	 * @param ent Normal Map Entity
	 */
	public void addNormalEntity(Entity ent){
		normalMapEntities.add(ent);
		System.out.println("Added: Normal Entity");
		System.out.println("Added: Entity " + entities.size());
	}

	/**
	 * Add Water Object to Render Engine
	 * @param water Water Object
	 */
	public void addWater(WaterTile water){
		waters.add(water);
	}

	/**
	 * Add GUI Texture to Render Engine
	 * @param texture GUI Texture
	 */
	public void addGUIImage(GuiTexture texture){
		guiTextures.add(texture);
	}

	public void setSkyboxSize(float size){
		renderer.setSkyboxSize(size);
	}

	public List<Terrain> getTerrains() {
		return terrains;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public List<Entity> getNormalMapEntities() {
		return normalMapEntities;
	}

	public List<Light> getLights() {
		return lights;
	}

	public List<GuiTexture> getGuiTextures() {
		return guiTextures;
	}

	public List<WaterTile> getWaters() {
		return waters;
	}


}
