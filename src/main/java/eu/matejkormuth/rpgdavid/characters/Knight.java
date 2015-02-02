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
package eu.matejkormuth.rpgdavid.characters;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.bukkit.inventory.Armor;
import eu.matejkormuth.bukkit.inventory.ItemStackBuilder;
import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Modifiers;
import eu.matejkormuth.rpgdavid.inventoryutils.ItemUtils;

public class Knight extends Character {
    public Knight() {
        super("Rytíř", "25% chance of armor not getting damaged.",
                new Modifiers(1.1F, 1, 1, 1, 1), new Armor(new ItemStack(
                        Material.IRON_HELMET), new ItemStack(
                        Material.IRON_CHESTPLATE), null, null), ItemUtils
                        .unbreaking(ItemStackBuilder.of(Material.IRON_SWORD)
                                .build(), 3));
    }
}
