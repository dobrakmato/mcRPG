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

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    public static final double CHAT_DISTANCE_SQUARED = 16 * 16;
    public static final String CHAT_FORMAT = ChatColor.GRAY + "%N: %M";

    @EventHandler
    private void onPlayerChat(final AsyncPlayerChatEvent event) {
        // Does not modify chat messages from Ops.
        if (event.getPlayer().isOp()) {
            return;
        }

        // Cancel event.
        event.setCancelled(true);
        // Apply filters.
        String msg = event.getMessage();
        msg = ChatFilters.ipFilter(msg);
        msg = ChatFilters.swearFilter(msg);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (event.getPlayer().getLocation()
                    .distanceSquared(player.getLocation()) < CHAT_DISTANCE_SQUARED) {
                // Player can recieve this message.
                player.sendMessage(CHAT_FORMAT.replace("%N",
                        event.getPlayer().getDisplayName()).replace("%M", msg));
            }
        }
    }

    private static class ChatFilters {
        private static Pattern IP_PATTERN = Pattern
                .compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                        + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                        + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                        + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

        public static String ipFilter(final String message) {
            return IP_PATTERN.matcher(message).replaceAll("[blocked]");
        }

        public static String swearFilter(String msg) {
            return msg;
        }
    }
}
