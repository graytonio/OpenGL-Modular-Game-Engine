package particles;

public abstract class ParticleSystem {
	private boolean active = true;
	
	protected abstract boolean generateParticles();
	
	public boolean isActive(){
		return active;
	}
	
	public void toggle(){
		active = !active;
	}
}
