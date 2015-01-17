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

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
            for (Entity e : event.getEntity().getNearbyEntities(
                    FIRE_SPELL_RADIUS, FIRE_SPELL_RADIUS, FIRE_SPELL_RADIUS)) {
                if (e instanceof LivingEntity) {
                    ((LivingEntity) e).damage(0.5D);
                    e.setFireTicks(20 * 10);
                }
            }
        } else if (event.getEntity().hasMetadata("freezingSpell")) {
            // Freezing spell.
            for (Entity e : event.getEntity().getNearbyEntities(
                    FREEZING_SPELL_RADIUS, FREEZING_SPELL_RADIUS,
                    FREEZING_SPELL_RADIUS)) {
                if(e instanceof LivingEntity) {
                    ((LivingEntity) e).damage(0.5D);
                    ((LivingEntity) e).addPotionEffect(new  PotionEffect(PotionEffectType.SLOW, 20 * 3, 0));
                }
            }
        }
    }
}
