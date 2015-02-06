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
package eu.matejkormuth.rpgdavid.starving.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackBase {
    protected ItemStack itemStack;

    public ItemStackBase(final Material material, final int amount,
            final String name) {
        this.itemStack = new ItemStack(material, amount);
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + name);
        this.itemStack.setItemMeta(meta);
    }

    /**
     * Checks whether specified {@link ItemStack} matches this
     * {@link ItemStackBase}.
     * 
     * @param obj
     *            ItemStack to compare to this ItemStackBase
     * @return true if this ItemStackBase does have same material and name as
     *         specified ItemStack, false otherwise
     */
    public boolean matches(final ItemStack obj) {
        if (obj == null) {
            return false;
        }

        if (this.itemStack == obj) {
            return true;
        }

        if (obj instanceof ItemStack) {
            return ((ItemStack) obj).getType().equals(this.itemStack.getType())
                    && ((ItemStack) obj).hasItemMeta()
                    && ((ItemStack) obj)
                            .getItemMeta()
                            .getDisplayName()
                            .equals(this.itemStack.getItemMeta()
                                    .getDisplayName());
        }
        return false;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
