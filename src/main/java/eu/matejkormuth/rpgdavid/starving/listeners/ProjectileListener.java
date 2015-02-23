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
import org.bukkit.block.Block;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.darkblade12.particleeffect.ParticleEffect;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.persistence.Persist;
import eu.matejkormuth.rpgdavid.starving.persistence.Persistable;

public class ProjectileListener extends Persistable implements Listener {
    @Persist(key = "PARTICLE_AMOUNT")
    private static int PARTICLE_AMOUNT = 25;

    @SuppressWarnings("deprecation")
    @EventHandler
    private void onProjectileHit(final ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Block b = event.getEntity().getLocation()
                    .add(event.getEntity().getVelocity().normalize())
                    .getBlock();
            // Disable snowball
            event.getEntity().remove();
            if (b.getType() != Material.AIR) {
                // Particle.
                ParticleEffect.BLOCK_CRACK.display(
                        new ParticleEffect.BlockData(b.getType(), b.getData()),
                        0.5f, 0.5f, 0.5f, 1, PARTICLE_AMOUNT, event.getEntity()
                                .getLocation(), Double.MAX_VALUE);
                // Break block.
                Starving.NMS.blockBreakAnimation(b.getLocation());
            }
        }
    }
}