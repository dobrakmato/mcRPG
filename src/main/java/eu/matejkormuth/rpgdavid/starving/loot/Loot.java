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
package eu.matejkormuth.rpgdavid.starving.loot;

import java.util.EnumSet;

import org.bukkit.Location;

import eu.matejkormuth.rpgdavid.starving.items.Category;
import eu.matejkormuth.rpgdavid.starving.items.Rarity;

public class Loot {
    private Location location;
    private EnumSet<Category> categories;
    private Rarity rarity;

    public Loot(Location location, EnumSet<Category> categories, Rarity rarity) {
        this.location = location;
        this.categories = categories;
        this.rarity = rarity;
    }

    public EnumSet<Category> getCategories() {
        return this.categories;
    }

    public Location getLocation() {
        return this.location;
    }

    public Rarity getRarity() {
        return this.rarity;
    }
}
