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

import java.util.EnumMap;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;

public class XPListener implements Listener {
    private static EnumMap<EntityType, Integer> xps;

    static {
        xps = new EnumMap<>(EntityType.class);
        xps.put(EntityType.BAT, 1);
        xps.put(EntityType.BLAZE, 5);
        xps.put(EntityType.CAVE_SPIDER, 4);
        xps.put(EntityType.CHICKEN, 1);
        xps.put(EntityType.COW, 1);
        xps.put(EntityType.CREEPER, 3);
        xps.put(EntityType.ENDERMAN, 7);
        xps.put(EntityType.ENDERMITE, 5);
        xps.put(EntityType.GHAST, 6);
        xps.put(EntityType.GIANT, 5);
        xps.put(EntityType.GUARDIAN, 10);
        xps.put(EntityType.HORSE, 1);
        xps.put(EntityType.IRON_GOLEM, 3);
        xps.put(EntityType.MAGMA_CUBE, 4);
        xps.put(EntityType.MUSHROOM_COW, 1);
        xps.put(EntityType.OCELOT, 1);
        xps.put(EntityType.PIG, 1);
        xps.put(EntityType.PIG_ZOMBIE, 4);
        xps.put(EntityType.RABBIT, 1);
        xps.put(EntityType.SHEEP, 1);
        xps.put(EntityType.SKELETON, 3);
        xps.put(EntityType.SLIME, 2);
        xps.put(EntityType.SNOWMAN, 1);
        xps.put(EntityType.SPIDER, 3);
        xps.put(EntityType.SQUID, 0);
        xps.put(EntityType.VILLAGER, 0);
        xps.put(EntityType.WITCH, 4);
        xps.put(EntityType.WITHER, 10);
        xps.put(EntityType.WOLF, 2);
        xps.put(EntityType.ZOMBIE, 5);
    }

    @EventHandler
    private void onEntityKilled(final EntityDamageByEntityEvent event) {
        // Only living entities.
        if (event.getEntity() instanceof LivingEntity) {
            // If is this killing hit.
            if (((Damageable) event.getEntity()).getHealth()
                    - event.getDamage() <= 0) {
                // If damager is player.
                if (event.getDamager() instanceof Player) {
                    Profile p = RpgPlugin.getInstance().getProfile(
                            (Player) event.getDamager());
                    if (p != null) {
                        // Give XP.
                        p.giveXp(xps.get(event.getEntityType()));
                    }
                }
            }
        }
    }
}
