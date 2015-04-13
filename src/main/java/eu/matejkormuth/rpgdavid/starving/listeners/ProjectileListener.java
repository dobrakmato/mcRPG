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

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import com.darkblade12.particleeffect.ParticleEffect;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.Time;
import eu.matejkormuth.rpgdavid.starving.persistence.AbstractPersistable;
import eu.matejkormuth.rpgdavid.starving.persistence.Persist;
import eu.matejkormuth.rpgdavid.starving.tasks.RepeatingTask;

public class ProjectileListener extends AbstractPersistable implements Listener {
    @Persist(key = "PARTICLE_AMOUNT")
    public static int PARTICLE_AMOUNT = 25;

    private Random random = new Random();

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
                Starving.NMS.displayMaterialBreak(b.getLocation());
            }
        } else if (event.getEntity() instanceof ThrownPotion) {
            if (event.getEntity().hasMetadata("isMolotov")) {
                for (int i = 0; i < 30; i++) {
                    FallingBlock block = event.getEntity().getWorld().spawnFallingBlock(
                            event.getEntity().getLocation().add(0, 1, 0),
                            Material.FIRE, (byte) 0);
                    block.setVelocity(new Vector(random.nextFloat() / 2, 0.2f,
                            random.nextFloat() / 2));
                }
            } else if (event.getEntity().hasMetadata("isSmokeShell")) {
                final Location location = event.getEntity().getLocation();
                RepeatingTask spawnParticles = new RepeatingTask() {
                    private int count = 0;

                    @Override
                    public void run() {
                        if (this.count >= 20 * 5) {
                            this.cancel();
                        }
                        this.spawn();
                        this.count++;
                    }

                    private void spawn() {
                        ParticleEffect.SMOKE_LARGE.display(5, 2, 5, 0, 40,
                                location, Double.MAX_VALUE);
                    }
                };

                spawnParticles.schedule(Time.ofTicks(1).toLongTicks());
            }
        }
    }
}
