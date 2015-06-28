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

import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.starving.loot.spawns.ArmorStandLootSpawn;

public class LootListener implements Listener {
    @EventHandler
    private void onArmorStandInteract(final PlayerInteractAtEntityEvent event) {
        Profile p = RpgPlugin.getInstance().getProfile(event.getPlayer());
        if (p != null) {
            if (event.getRightClicked() instanceof ArmorStand) {
                if (!event.getRightClicked().hasMetadata(
                        ArmorStandLootSpawn.METADATA_KEY)) {
                    // This is not loot ArmorStand.
                    return;
                }

                if (((ArmorStand) event.getRightClicked()).getItemInHand() != null) {
                    event.getPlayer()
                            .getInventory()
                            .addItem(
                                    ((ArmorStand) event.getRightClicked())
                                            .getItemInHand());
                    event.getPlayer().playSound(
                            event.getPlayer().getLocation(), Sound.ITEM_PICKUP,
                            1, 1);
                    event.getRightClicked().remove();
                }
                event.setCancelled(true);
            }
        }
    }
}
