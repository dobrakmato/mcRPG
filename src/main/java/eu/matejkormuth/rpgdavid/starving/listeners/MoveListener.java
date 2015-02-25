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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.persistence.Persist;
import eu.matejkormuth.rpgdavid.starving.persistence.AbstractPersistable;

public class MoveListener extends AbstractPersistable implements Listener {
    @Persist(key = "STAMINA_LEVEL_REQUIRED_FOR_SPRINT")
    private static int STAMINA_LEVEL_REQUIRED_FOR_SPRINT = 200;

    // Performance critical code.
    @EventHandler
    private void onPlayerMove(final PlayerMoveEvent event) {
        float dist = (float) event.getTo().distanceSquared(event.getFrom());
        Data.of(event.getPlayer()).decrementStamina(dist);
    }

    @EventHandler
    private void onSprint(final PlayerToggleSprintEvent event) {
        // Only if player has stamina.
        Data d = Data.of(event.getPlayer());

        if (!d.isAbleToSprint()
                || d.getStamina() < STAMINA_LEVEL_REQUIRED_FOR_SPRINT) {
            event.setCancelled(true);
        }
    }
}
