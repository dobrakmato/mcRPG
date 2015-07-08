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
package eu.matejkormuth.rpgdavid.starving.items.explosives;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.bukkit.Actions;
import eu.matejkormuth.bukkit.ItemDrops;
import eu.matejkormuth.rpgdavid.starving.Scheduler;
import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.Time;
import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.Mappings;
import eu.matejkormuth.rpgdavid.starving.items.Rarity;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;

public class Grenade extends Item {
    public Grenade() {
        super(Mappings.GRENADE, "Grenade");
        this.setMaxStackAmount(8);
        this.setRarity(Rarity.RARE);
        // TODO: Category.
    }

    @Override
    public InteractResult onInteract(Player player, Action action,
            Block clickedBlock, BlockFace clickedFace) {
        if (Actions.isRightClick(action)) {
            // Throw grenade.

            // Play sound.
            Starving.NMS.playNamedSoundEffect(player,
                    "pyrotechnics.grenade.throw",
                    player.getLocation(), 1.5f, 1f);
            for (Entity e : player.getNearbyEntities(32, 32, 32)) {
                if (e.getType() == EntityType.PLAYER) {
                    Starving.NMS.playNamedSoundEffect((Player) e,
                            "pyrotechnics.grenade.throw",
                            player.getLocation(), 1.5f, 1f);
                }
            }

            ItemStack itemStack = new ItemStack(Mappings.GRENADE.getMaterial(),
                    1);
            org.bukkit.entity.Item drop = ItemDrops.dropNotMergable(
                    player.getLocation().add(
                            player.getEyeLocation().getDirection()), itemStack,
                    Time.ofSeconds(6).toTicks());
            drop.setVelocity(player.getEyeLocation().getDirection().multiply(
                    1.5f));

            // Schedule explosion.
            Scheduler.delay(() -> {
                // Play sound.
                    Starving.NMS.playNamedSoundEffect(player,
                            "pyrotechnics.grenade.explode",
                            player.getLocation(), 1.5f, 1f);
                    for (Entity e : player.getNearbyEntities(32, 32, 32)) {
                        if (e.getType() == EntityType.PLAYER) {
                            Starving.NMS.playNamedSoundEffect((Player) e,
                                    "pyrotechnics.grenade.explode",
                                    player.getLocation(), 1.5f, 1f);
                        }
                    }
                    drop.remove();
                }, Time.ofSeconds(5));
        }
        return InteractResult.useOne();
    }
}
