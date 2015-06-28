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
import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Modifiers;
import eu.matejkormuth.rpgdavid.RpgPlugin;

public final class Soldier extends Character {
    public Soldier() {
        super("Válečník", RpgPlugin.getInstance().getConfig()
                .getString("translation_soldier"), new Modifiers(1, 1, 1.2F, 1,
                1), new Armor(new ItemStack(Material.CHAINMAIL_HELMET),
                new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(
                        Material.CHAINMAIL_LEGGINGS), new ItemStack(
                        Material.CHAINMAIL_BOOTS)), unbreaking(
                new ItemStack(Material.IRON_AXE), 3));
    }
}
