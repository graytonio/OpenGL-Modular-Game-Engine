package entities;

import org.lwjgl.util.vector.Vector3f;

public abstract class Camera {
	
	protected Entity focus;
	
	private Vector3f position = new Vector3f(0, 0, 0);
	protected float pitch = 20;
	protected float yaw = 0;
	protected float roll;
	
	public Camera(Entity focus) {
		this.focus=focus;
	}
	
	public abstract void move();
	
	public void invertPitch() {
		this.pitch = -pitch;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
}
