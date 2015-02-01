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
package eu.matejkormuth.rpgdavid.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GrapplingHook extends ItemStack {
    public static final double MAX_USE_DISTANCE = 6.0D;
    private static final String HOOK_NAME = ChatColor.RESET + "Grappling Hook";

    public GrapplingHook() {
        super(Material.CARROT_STICK);

        ItemMeta im = this.getItemMeta();
        im.setDisplayName(HOOK_NAME);
        this.setItemMeta(im);
    }

    public static boolean isHook(ItemStack itemInHand) {
        if (itemInHand == null)
            return false;

        return itemInHand.getType().equals(Material.CARROT_STICK)
                && itemInHand.hasItemMeta()
                && itemInHand.getItemMeta().getDisplayName().equals(HOOK_NAME);
    }
}
