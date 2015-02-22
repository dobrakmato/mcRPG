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
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.darkblade12.particleeffect.ParticleEffect;

public class HeadshotListener implements Listener {
    private static final double EPSILON = 0.4;

    @EventHandler
    private void onHeadshot(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Projectile) {
            if (event.getEntity() instanceof LivingEntity) {
                // Check for headshot.
                if (Math.abs(event.getDamager().getLocation().getY()
                        - ((LivingEntity) event.getEntity()).getEyeLocation()
                                .getY()) < EPSILON) {
                    ParticleEffect.BLOCK_CRACK.display(
                            new ParticleEffect.BlockData(
                                    Material.REDSTONE_BLOCK, (byte) 0), 0.25f,
                            0.25f, 0.25f, 1, 80, ((LivingEntity) event
                                    .getEntity()).getEyeLocation(), 256);
                    event.getEntity()
                            .getWorld()
                            .playSound(event.getEntity().getLocation(),
                                    Sound.HURT_FLESH, 1, 1);
                    for (int i = 0; i < 20; i++) {
                        ParticleEffect.BLOCK_CRACK.display(
                                new ParticleEffect.BlockData(
                                        Material.REDSTONE_BLOCK, (byte) 0),
                                event.getDamager().getVelocity().multiply(5),
                                1,
                                ((LivingEntity) event.getEntity())
                                        .getEyeLocation().add(
                                                Math.random() - 0.5,
                                                Math.random() - 0.5,
                                                Math.random() - 0.5),
                                Double.MAX_VALUE);
                        // TODO: Improve particle effect on headshot.
                    }
                    event.setDamage(9999999D);
                }
            }
        }
    }
}
