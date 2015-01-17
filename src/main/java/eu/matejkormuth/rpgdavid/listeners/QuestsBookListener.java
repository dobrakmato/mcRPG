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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.QuestsBook;
import eu.matejkormuth.rpgdavid.RpgPlugin;

public class QuestsBookListener implements Listener {
    @EventHandler
    private void onOpenBook(final PlayerInteractEvent event) {
        Profile p = RpgPlugin.getInstance().getProfile(event.getPlayer());
        if (p != null) {
            if (p.hasCharacter()) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR
                        || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    if (event.hasItem() && QuestsBook.isQuestBook(event.getItem())) {
                        // Update book contents before opening.
                        QuestsBook.update(event.getItem());
                        // TODO: Update book contents before opening book by player.
                    }
                }
            }
        }
    }
}
