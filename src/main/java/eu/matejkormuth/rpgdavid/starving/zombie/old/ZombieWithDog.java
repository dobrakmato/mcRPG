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
package eu.matejkormuth.rpgdavid.starving.zombie.old;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;

import eu.matejkormuth.rpgdavid.starving.Starving;

public class ZombieWithDog {
    public static void spawn(Location location) {
        Zombie zombie = Starving.getInstance().getZombieManager()
                .spawnAt(location);
        Wolf dog = (Wolf) location.getWorld().spawnEntity(location,
                EntityType.WOLF);
        dog.setAngry(true);
        dog.setLeashHolder(zombie.getBukkitEntity());
    }
}
