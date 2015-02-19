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

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import eu.matejkormuth.rpgdavid.starving.Data;

public class BloodLevelDamageListener implements Listener {
    @EventHandler
    private void onPlayerDamageEntity(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            float bloodAmount = Data.of((Player) event.getDamager())
                    .getBloodLevel();

            // Consequences of low blood level. (more in BloodLevelConsuquencesTask)
            if (bloodAmount < 3000) {
                event.setDamage(event.getDamage() * 0.25);
            } else if (bloodAmount < 4000) {
                event.setDamage(event.getDamage() * 0.75);
            }
        }
    }
}
