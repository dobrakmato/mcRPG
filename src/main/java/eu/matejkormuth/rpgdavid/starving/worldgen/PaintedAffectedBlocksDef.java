package eu.matejkormuth.rpgdavid.starving.worldgen;

import org.bukkit.Location;

public abstract class PaintedAffectedBlocksDef {
	protected Location center;
	protected int radius;
	protected int radiusPow2;

	public PaintedAffectedBlocksDef(int radius, Location center) {
		this.center = center;
		this.radius = radius;
		this.radiusPow2 = radius * radius;
	}

	public Location getCenter() {
		return center;
	}

	public int getRadius() {
		return radius;
	}
	
	public boolean hasCenter() {
		return true;
	}
}
