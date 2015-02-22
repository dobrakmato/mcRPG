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
package eu.matejkormuth.rpgdavid.starving.listeners;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import com.darkblade12.particleeffect.ParticleEffect;

public class ExplosionListener implements Listener {
    private static final Vector FALLING_BLOCK_ADD_VECTOR = new Vector(0, 2, 0);

    @SuppressWarnings("deprecation")
    @EventHandler
    private void onExposion(final EntityExplodeEvent event) {
        // Display bunch of effects.
        ParticleEffect.SMOKE_LARGE.display(5, 2, 5, 0.5f, 700,
                event.getLocation(), Double.MAX_VALUE);
        ParticleEffect.SMOKE_LARGE.display(7, 2, 7, 0.01f, 1000,
                event.getLocation(), Double.MAX_VALUE);
        ParticleEffect.EXPLOSION_NORMAL.display(5, 2, 5, 0.5f, 200,
                event.getLocation(), Double.MAX_VALUE);
        ParticleEffect.SUSPENDED_DEPTH.display(10, 5, 10, 0, 800,
                event.getLocation(), Double.MAX_VALUE);
        // Make blocks fly to air.
        World w = event.getLocation().getWorld();
        for (Block b : event.blockList()) {
            if (b.getType().isBlock()) {
                if (b.getType() == Material.GRASS) {
                    w.spawnFallingBlock(
                            b.getLocation().add(FALLING_BLOCK_ADD_VECTOR),
                            Material.DIRT, b.getData()).setDropItem(false);
                } else {
                    w.spawnFallingBlock(
                            b.getLocation().add(FALLING_BLOCK_ADD_VECTOR),
                            b.getType(), b.getData()).setDropItem(false);

                }
            }
            b.setType(Material.AIR);
        }
        // Clear damaged blocks.
        event.blockList().clear();
    }
}
