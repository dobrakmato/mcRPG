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
package eu.matejkormuth.rpgdavid.starving.items.base;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.matejkormuth.rpgdavid.starving.items.Mapping;

public abstract class BlockWithData extends Item {

    private byte data;

    public BlockWithData(Mapping mapping, String name, byte data) {
        super(mapping, name);
        this.data = data;
    }

    @SuppressWarnings("deprecation")
    public void onPlaced(Player player, Block blockPlaced) {
        blockPlaced.setData(data, true);
    }

    @Override
    public ItemStack toItemStack() {
        return this.toItemStack(1);
    }

    @Override
    public ItemStack toItemStack(int amount) {
        ItemStack is = super.toItemStack(amount);
        ItemMeta im = is.getItemMeta();
        im.setLore(Arrays.asList(ChatColor.GREEN + "Data: " + data));
        is.setItemMeta(im);
        return is;
    }

}
