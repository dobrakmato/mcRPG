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

import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;

public class HiddenCommandsListener implements Listener {
    @EventHandler
    private void onCommand(final PlayerCommandPreprocessEvent event) {
        if (event.getMessage().equalsIgnoreCase("/items")) {
            List<Item> items = Starving.getInstance().getItemManager()
                    .getItems();
            for (int j = 0; j < items.size(); j++) {
                Item i = items.get(j);
                event.getPlayer().sendMessage(j + " - " + i.getName());
            }
        } else if (event.getMessage().contains("/itemsgive")) {
            List<Item> items = Starving.getInstance().getItemManager()
                    .getItems();
            for (int j = 0; j < items.size(); j++) {
                Item i = items.get(j);
                if (j == Integer.valueOf(event.getMessage().split(
                        Pattern.quote(" "))[1])) {
                    event.getPlayer().getInventory().addItem(i.toItemStack());
                }

            }
        }
    }
}
