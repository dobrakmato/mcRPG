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
import org.bukkit.inventory.Recipe;

import eu.matejkormuth.rpgdavid.starving.items.Category;
import eu.matejkormuth.rpgdavid.starving.items.Rarity;
import eu.matejkormuth.rpgdavid.starving.items.base.Craftable;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;

public class Transmitter extends Item implements Craftable {
    public Transmitter() {
        super(Material.IRON_BARDING, "Transmitter");
        // Item properties.
        this.setMaxStackAmount(1);
        this.setCategory(Category.ELECTRONICS);
        this.setRarity(Rarity.UNCOMMON);
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }
}
