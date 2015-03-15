/*
 *  Starving is a open source bukkit/spigot mmo game.
 *  Copyright (C) 2014-2015 Matej Kormuth
 *  This file is a part of Starving. <http://www.starving.eu>
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
package eu.matejkormuth.rpgdavid.starving.items.firearms;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

import com.darkblade12.particleeffect.ParticleEffect;

import eu.matejkormuth.rpgdavid.starving.items.AmunitionType;
import eu.matejkormuth.rpgdavid.starving.items.Mappings;
import eu.matejkormuth.rpgdavid.starving.items.base.Firearm;

public class Mossberg500 extends Firearm {

	public Mossberg500() {
		this(Mappings.MOSSBERG500, "Mossberg 500");
	}

	public Mossberg500(Material mapping, String name) {
		super(mapping, name);
		this.setAmmoType(AmunitionType.LONG);
		this.setClipSize(6);
		this.setFireRate(1);
		this.setInaccurancy(0.5f);
		this.setScopedInaccurancy(0.05f);
		this.setNoiseLevel(1);
		this.setProjectileSpeed(2f);
		this.setRecoil(0.6f);
		this.setReloadTime(40);
	}

	@Override
	protected Vector computeAndFire(Player player) {
		Vector projectileVelocity = fire(player);
		for (int i = 0; i < 5; i++) {
			fire(player);
		}
		return projectileVelocity;
	}

	private Vector fire(Player player) {
		// Compute values.
		Location projectileSpawn = player.getEyeLocation().add(
				player.getEyeLocation().getDirection());
		Vector randomVec = Vector.getRandom().subtract(HALF_VECTOR)
				.multiply(this.getInaccurancy());
		Vector projectileVelocity = player.getEyeLocation().getDirection()
				.add(randomVec).multiply(this.getProjectileSpeed());

		// Spawn projectile.
		Snowball projectile = (Snowball) player.getWorld().spawnEntity(
				projectileSpawn, EntityType.SNOWBALL);
		projectile.setVelocity(projectileVelocity);
		ParticleEffect.SMOKE_NORMAL.display(0.1f, 0.1f, 0.1f, 0, 20,
				projectileSpawn, Double.MAX_VALUE);
		// Display effect.
		return projectileVelocity;
	}
}
