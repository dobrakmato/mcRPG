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
package eu.matejkormuth.rpgdavid.starving.items.ranged;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.Mappings;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;
import eu.matejkormuth.rpgdavid.starving.items.transformers.CrossbowTransformer;

public class LoadedCrossbow extends Item {
	private float projectileSpeed = 2f;

	public LoadedCrossbow() {
		super(Mappings.CROSSBOWLOADED, "Crossbow (loaded)");
	}

	@Override
	public InteractResult onInteract(Player player, Action action,
			Block clickedBlock, BlockFace clickedFace) {
		// Fire arrow.
		this.fire(player);
		// Transform.
		ItemStack unloaded = CrossbowTransformer.toUnloaded();
		player.setItemInHand(unloaded);
		return InteractResult.transform();
	}

	private void fire(Player player) {
		// Compute values.
		Location projectileSpawn = player.getEyeLocation().add(
				player.getEyeLocation().getDirection().multiply(2));
		Vector projectileVelocity = player.getEyeLocation().getDirection()
				.multiply(this.projectileSpeed);
		// Create entity and set velocity.
		Arrow arrow = (Arrow) player.getWorld().spawnEntity(projectileSpawn,
				EntityType.ARROW);
		arrow.setVelocity(projectileVelocity);
		// Play sound.
		player.getWorld().playSound(projectileSpawn, Sound.IRONGOLEM_THROW,
				2.5f, 1f);
	}

}
