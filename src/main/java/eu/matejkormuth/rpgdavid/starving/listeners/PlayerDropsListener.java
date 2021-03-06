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

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.util.EulerAngle;

import eu.matejkormuth.rpgdavid.bukkitfixes.FlagMetadataValue;
import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.items.base.Firearm;
import eu.matejkormuth.rpgdavid.starving.loot.spawns.ArmorStandLootSpawn;

public class PlayerDropsListener implements Listener {

    @EventHandler
    private void onPlayerDropItem(final PlayerDropItemEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);

            ArmorStand armorStand = (ArmorStand) event.getPlayer().getWorld().spawnEntity(
                    event.getPlayer().getEyeLocation(), EntityType.ARMOR_STAND);
            armorStand.setVelocity(event.getPlayer().getEyeLocation().getDirection());
            armorStand.setItemInHand(event.getItemDrop().getItemStack());
            armorStand.setVisible(false);
            armorStand.setArms(true);
            armorStand.setBasePlate(false);
            float minusY = 0.8f;

            if (Starving.getInstance().getItemManager().findItem(
                    event.getItemDrop().getItemStack()) instanceof Firearm) {
                armorStand.setRightArmPose(new EulerAngle(0, 0, Math.PI / 2));
                minusY = 1.27f;
            }

            armorStand.setMetadata(ArmorStandLootSpawn.METADATA_KEY,
                    new FlagMetadataValue());

            float finalMinusY = minusY;
            Bukkit.getScheduler().scheduleSyncDelayedTask(
                    Starving.getInstance().getPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            armorStand.teleport(armorStand.getLocation().subtract(
                                    0, finalMinusY, 0));
                            armorStand.setGravity(false);
                        }
                    }, 30L);
        }
    }
}
