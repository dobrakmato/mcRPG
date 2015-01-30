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
package eu.matejkormuth.rpgdavid.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpellsListener implements Listener {
    public static final double FIRE_SPELL_RADIUS = 5.0D;
    public static final double FREEZING_SPELL_RADIUS = 5.0D;

    // Fire and freezing spell.
    @EventHandler
    private void onFireballHit(final ProjectileHitEvent event) {
        if (event.getEntity().hasMetadata("fireSpell")) {
            // Fire spell.
            onFireSpell(event);
        } else if (event.getEntity().hasMetadata("freezingSpell")) {
            // Freezing spell.
            onFreezingSpell(event);
        }
    }

    @EventHandler
    private void onTntExplode(final EntityExplodeEvent event) {
        if (event.getEntity() instanceof TNTPrimed) {
            if (event.getEntity().hasMetadata("tntSpell")) {

            }
        }
    }

    private void onFreezingSpell(final ProjectileHitEvent event) {
        for (Entity e : event.getEntity().getNearbyEntities(
                FREEZING_SPELL_RADIUS, FREEZING_SPELL_RADIUS,
                FREEZING_SPELL_RADIUS)) {
            if (e instanceof LivingEntity) {
                e.getWorld()
                        .playSound(e.getLocation(), Sound.GLASS, 0.5f, 0.5f);

                // Entities should not be extinguished.

                ((LivingEntity) e).damage(0.5D);
                ((LivingEntity) e).addPotionEffect(new PotionEffect(
                        PotionEffectType.SLOW, 20 * 3, 0));

                // Freeze water blocks.
                BlockState b = null;
                for (int x = e.getLocation().getBlockX() - 2; x <= e
                        .getLocation().getBlockX() + 2; x++) {
                    for (int y = e.getLocation().getBlockY() - 1; y <= e
                            .getLocation().getBlockY() + 1; y++) {
                        for (int z = e.getLocation().getBlockZ() - 2; z <= e
                                .getLocation().getBlockZ() + 2; z++) {
                            b = e.getWorld().getBlockAt(x, y, z).getState();
                            if (b.getType() == Material.STATIONARY_WATER
                                    || b.getType() == Material.WATER) {
                                b.setType(Material.ICE);
                                b.update();
                            }
                        }
                    }
                }
            }
        }
    }

    private void onFireSpell(final ProjectileHitEvent event) {
        for (Entity e : event.getEntity().getNearbyEntities(FIRE_SPELL_RADIUS,
                FIRE_SPELL_RADIUS, FIRE_SPELL_RADIUS)) {
            if (e instanceof LivingEntity) {

                ((LivingEntity) e).damage(0.5D);
                e.setFireTicks(20 * 10);
            }
        }
    }
}
