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
package eu.matejkormuth.rpgdavid.starving.achievements.kill;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import eu.matejkormuth.rpgdavid.starving.achievements.Achievement;

public class Ares extends Achievement {

    public Ares() {
        super("Ares", "Kill 1000 zombies.", 1000);
    }

    @EventHandler
    private void onEntityKilled(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            // Check if entity dies in this hit.
            if (event.getEntity() instanceof LivingEntity &&
                    ((LivingEntity) event).getHealth() - event.getFinalDamage() <= 0) {
                // Check if he killed zombie.
                if (event.getEntityType() == EntityType.ZOMBIE) {
                    this.progress((Player) event.getDamager(), 1);
                }
            }
        }
    }

}
