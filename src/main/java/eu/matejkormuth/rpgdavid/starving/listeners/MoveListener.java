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

import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;

public class MoveListener implements Listener {
    // Profile cache.
    private Map<Player, Profile> profiles;

    public MoveListener() {
        profiles = new WeakHashMap<>();
    }

    // Performance critical code.
    @EventHandler
    private void onPlayerMove(final PlayerMoveEvent event) {
        Profile p = this.profiles.get(event.getPlayer());
        if (p == null) {
            p = RpgPlugin.getInstance().getProfile(event.getPlayer());
            this.profiles.put(event.getPlayer(), p);
        }

        float dist = (float) event.getTo().distanceSquared(event.getFrom());
        p.decrementStamina(dist / 4);
    }

    @EventHandler
    private void onSprint(final PlayerToggleSprintEvent event) {
        // Only if player has stamina.
        Profile p = this.profiles.get(event.getPlayer());
        if (p == null) {
            p = RpgPlugin.getInstance().getProfile(event.getPlayer());
            this.profiles.put(event.getPlayer(), p);
        }

        if (p.getStamina() < 50) {
            event.setCancelled(true);
        }
    }
}
