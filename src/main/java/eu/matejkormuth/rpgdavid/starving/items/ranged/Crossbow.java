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
package eu.matejkormuth.rpgdavid.starving.items.ranged;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.Mappings;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;
import eu.matejkormuth.rpgdavid.starving.items.transformers.CrossbowTransformer;

public class Crossbow extends Item {

    public Crossbow() {
        super(Mappings.CORSSBOWUNLOADED, "Crossbow");
    }

    @Override
    public InteractResult onInteract(Player player, Action action,
            Block clickedBlock, BlockFace clickedFace) {
        if (player.getInventory().contains(Material.ARROW)) {
            player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0f,
                    0.5f);
            // Remove one arrow.
            player.getInventory().remove(new ItemStack(Material.ARROW, 1));

            ItemStack loaded = CrossbowTransformer.toLoaded();
            player.setItemInHand(loaded);
        } else {
            // Player has no arrows.
            player.sendMessage(ChatColor.RED
                    + "Crossbow is loaded with arrows!");
        }

        return InteractResult.transform();
    }

}
