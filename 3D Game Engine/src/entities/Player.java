package entities;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import terrains.Terrain;

public abstract class Player extends Entity {

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, float width, float height, float depth) {
		super(model, position, rotX, rotY, rotZ, scale, width, height, depth);
	}
	
	public void move(Terrain ter){
	}
	
	public void move(Terrain ter, List<Entity> entities){
	}
	public abstract void checkInputs();
	
}
