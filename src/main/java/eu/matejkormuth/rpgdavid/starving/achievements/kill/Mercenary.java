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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import eu.matejkormuth.rpgdavid.starving.achievements.Achievement;

public class Mercenary extends Achievement {

    private List<EntityType> enemyList = new ArrayList<>();

    public Mercenary() {
        super("Mercenary", "Kill 100 enemies.", 100);
    }

    @EventHandler
    private void onEntityKilled(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            // Check if entity dies in this hit.
            if (event.getEntity() instanceof LivingEntity &&
                    ((LivingEntity) event).getHealth() - event.getFinalDamage() <= 0) {
                // Check if he killed an enemy.
                if (isEnemy(event.getEntity())) {
                    this.progress((Player) event.getDamager(), 1);
                }
            }
        }
    }

    private boolean isEnemy(Entity entity) {
        return enemyList.contains(entity.getType());
    }

}
