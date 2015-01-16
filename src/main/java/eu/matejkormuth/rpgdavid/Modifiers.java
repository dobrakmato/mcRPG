/*
 *  mcRPG is a open source rpg bukkit/spigot plugin.
 *  Copyright (C) 2015 Matej Kormuth 
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
