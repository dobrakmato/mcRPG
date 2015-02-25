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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import eu.matejkormuth.bukkit.Worlds;
import eu.matejkormuth.rpgdavid.starving.persistence.Persist;
import eu.matejkormuth.rpgdavid.starving.persistence.AbstractPersistable;

public class ZombieManager extends AbstractPersistable {
    // Zombie speed constatnts.
    @Persist(key = "LOW_SPEED")
    public static double LOW_SPEED = 0.6d;
    @Persist(key = "NORMAL_SPEED")
    public static double NORMAL_SPEED = 1.0d;
    @Persist(key = "HIGH_SPEED")
    public static double HIGH_SPEED = 1.5d;

    private final ZombiePool pool;
    private List<WeakReference<Zombie>> zombies;

    @Persist(key = "poolLocation")
    private Location poolLocation = new Location(Worlds.first(), 1122, 76, 576);

    @Persist(key = "poolSize")
    private int poolSize;

    public ZombieManager() {
        this.pool = new ZombiePool(this.poolLocation, this.poolSize);
        this.zombies = new ArrayList<>();
    }

    public void add() {
        this.pool.acquire();
    }

    public void remove(Entity e) {

    }

    public Zombie spawnAt(Location location) {
        Zombie z = new Zombie(location);
        this.zombies.add(new WeakReference<Zombie>(z));
        return z;
    }

    public Zombie get(int entity) {
        for (WeakReference<Zombie> z : this.zombies) {
            if (z.get() != null) {
                if (z.get().getId() == entity) {
                    return z.get();
                }
            }
        }
        return null;
    }
}
