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
package eu.matejkormuth.rpgdavid.starving.items.itemmeta;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class ItemMetaWrapper implements LoreAccessor {
    protected ItemStack itemStack;
    protected ItemMeta meta;
    protected KeyValueHandler valueHandler;

    public ItemMetaWrapper(ItemStack stack) {
        this.itemStack = stack;
        this.meta = stack.getItemMeta();
        this.valueHandler = new StdLoreHandler(this);
    }

    public ItemMetaWrapper(ItemStack stack, KeyValueHandler valueHandler) {
        this.meta = stack.getItemMeta();
        this.valueHandler = valueHandler;
    }

    @Override
    public List<String> getLore() {
        if (this.meta.getLore() == null) {
            return new ArrayList<>();
        }

        return this.meta.getLore();
    }

    @Override
    public void setLore(List<String> lore) {
        this.meta.setLore(lore);
    }

    public void apply(ItemStack is) {
        is.setItemMeta(this.meta);
    }

    public ItemStack apply() {
        this.itemStack.setItemMeta(this.meta);
        return this.itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
