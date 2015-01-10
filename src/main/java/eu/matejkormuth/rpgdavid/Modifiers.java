package eu.matejkormuth.rpgdavid;

public class Modifiers {
	public static final Modifiers DEFAULT = new Modifiers(1, 1, 1, 1, 1);
	
	private final float healthModifier;
	private final float speedModifier;
	private final float dmgModifier;
	private final float criticalModifier;
	private final float healthRegainModifier;
	
	public Modifiers(float healthModifier, float speedModifier,
			float dmgModifier, float criticalModifier, float healthRegainModifier) {
		this.healthModifier = healthModifier;
		this.speedModifier = speedModifier;
		this.dmgModifier = dmgModifier;
		this.criticalModifier = criticalModifier;
		this.healthRegainModifier = healthRegainModifier;
	}
	
	public float getWalkSpeedModifier() {
		return speedModifier;
	}
	
	public float getCriticalModifier() {
		return criticalModifier;
	}
	
	public float getDamageModifier() {
		return dmgModifier;
	}
	
	public float getHealthModifier() {
		return healthModifier;
	}

	public float getHealthRegainModifier() {
		return healthRegainModifier;
	}
}
