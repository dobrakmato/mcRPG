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
package eu.matejkormuth.rpgdavid.starving.zombie;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import eu.matejkormuth.rpgdavid.starving.PersistInjector;
import eu.matejkormuth.rpgdavid.starving.annotations.Persist;

public class ZombieManager {
    // Zombie speed constatnts.
    public static final double LOW_SPEED = 0.6d;
    public static final double NORMAL_SPEED = 1.0d;
    public static final double HIGH_SPEED = 1.5d;

    private final ZombiePool pool;

    @Persist(key = "poolLocation")
    private Location poolLocation;

    public ZombieManager() {
        // Load configuration values.
        PersistInjector.inject(this);

        this.pool = new ZombiePool(poolLocation, 20);
    }

    public void add() {
        this.pool.acquire();
    }

    public void remove(Entity e) {
        // TODO Auto-generated method stub

    }
}
