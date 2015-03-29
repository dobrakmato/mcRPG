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
package eu.matejkormuth.rpgdavid.starving.items.misc;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import eu.matejkormuth.bukkit.Actions;
import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.Mapping;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;
import eu.matejkormuth.rpgdavid.starving.items.itemmeta.concrete.FlashlightItemMetaWrapper;

public class Flashlight extends Item {

    public Flashlight() {
        super(new Mapping(Material.BLAZE_POWDER), "Fleshlight");
    }

    @Override
    public InteractResult onInteract(Player player, Action action,
            Block clickedBlock, BlockFace clickedFace) {
        if (Actions.isRightClick(action)) {
            switchState(player);
            player.getWorld().playSound(player.getLocation(), Sound.CLICK,
                    0.8f, 2.0f);
        }
        return super.onInteract(player, action, clickedBlock, clickedFace);
    }

    private void switchState(Player player) {
        Data data = Data.of(player);
        FlashlightItemMetaWrapper wrapper = new FlashlightItemMetaWrapper(
                player.getItemInHand());
        if (data.isFlashlightOn()) {

            data.setFlashlightOn(false);
            wrapper.setSwitchedOn(false);
        } else {

            data.setFlashlightOn(true);
            wrapper.setSwitchedOn(true);
        }
        player.setItemInHand(wrapper.apply());

    }

}
