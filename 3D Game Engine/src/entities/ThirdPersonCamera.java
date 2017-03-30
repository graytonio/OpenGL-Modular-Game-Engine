package entities;

import org.lwjgl.input.Mouse;

public class ThirdPersonCamera extends Camera{

	private float distanceFromPlayer = 35;
	private float angleAroundPlayer = 0;
	private Player player;
	private boolean trackPlayer=false;

	public ThirdPersonCamera(Player player) {
		super(player);
		this.player = player;
	}

	public void move() {
		updateValues();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		yaw = 180 - (super.focus.getRotY() + angleAroundPlayer);
		yaw %= 360;
	}

	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = super.focus.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		super.getPosition().x = super.focus.getPosition().x - offsetX;
		super.getPosition().z = super.focus.getPosition().z - offsetZ;
		super.getPosition().y = super.focus.getPosition().y + verticDistance + 4;
	}
	
	public void updateValues(){
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();		
	}

	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch + 4)));
	}

	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch + 4)));
	}

	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.03f;
		distanceFromPlayer -= zoomLevel;
		if (distanceFromPlayer < 5) {
			distanceFromPlayer = 5;
		}
	}

	private void calculatePitch() {
		if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY() * 0.2f;
			pitch -= pitchChange;
			if (pitch < 0) {
				pitch = 0;
			} else if (pitch > 90) {
				pitch = 90;
			}
		}
	}

	private void calculateAngleAroundPlayer() {
		if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
			float angleChange = Mouse.getDX() * 0.3f;
			if(!trackPlayer) angleAroundPlayer -= angleChange;
			if(trackPlayer) this.player.increaseRotation(0, angleChange, 0);
		}
	}
	
	private void cameraMode(){
		if(Mouse.isButtonDown(4)){
			this.trackPlayer=!this.trackPlayer;
			reset();
		}
	}
	
	public void reset(){
		distanceFromPlayer = 35;
		angleAroundPlayer = 0;
		pitch = 20;
		yaw = 0;
		roll=0;
	}

}
