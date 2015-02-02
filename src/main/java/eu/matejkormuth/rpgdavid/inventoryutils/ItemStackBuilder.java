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
package eu.matejkormuth.rpgdavid.inventoryutils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

public class ItemStackBuilder {
    private ItemStack itemStack;

    public ItemStackBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemStackBuilder name(String name) {
        ItemMeta im = this.itemStack.getItemMeta();
        im.setDisplayName(ChatColor.RESET + name);
        this.itemStack.setItemMeta(im);
        return this;
    }

    public ItemStackBuilder lore(String lore) {
        ItemMeta im = this.itemStack.getItemMeta();
        im.setLore(Arrays.asList(lore));
        this.itemStack.setItemMeta(im);
        return this;
    }

    public ItemStackBuilder lore(List<String> lores) {
        ItemMeta im = this.itemStack.getItemMeta();
        im.setLore(lores);
        this.itemStack.setItemMeta(im);
        return this;
    }

    public ItemStackBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder durability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    public ItemStackBuilder color(Color color) {
        ItemMeta im = this.itemStack.getItemMeta();
        if (im instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) im).setColor(color);
        } else {
            throw new UnsupportedOperationException("ItemStack of "
                    + this.itemStack.getType().toString()
                    + " is not colorable!");
        }
        this.itemStack.setItemMeta(im);
        return this;
    }

    public ItemStackBuilder owner(String owner) {
        ItemMeta im = this.itemStack.getItemMeta();
        if (im instanceof SkullMeta) {
            ((SkullMeta) im).setOwner(owner);
        } else {
            throw new UnsupportedOperationException("ItemStack of "
                    + this.itemStack.getType().toString()
                    + " can't have owener!");
        }
        this.itemStack.setItemMeta(im);
        return this;
    }

    @Deprecated
    public ItemStackBuilder data(byte data) {
        this.itemStack
                .setData(new MaterialData(this.itemStack.getType(), data));
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }

    public static ItemStackBuilder of(Material material) {
        return new ItemStackBuilder(material);
    }
}
