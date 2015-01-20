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

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class ZombieManager {
    // Zombie speed constatnts.
    public static final double LOW_SPEED = 0.6d;
    public static final double NORMAL_SPEED = 1.0d;
    public static final double HIGH_SPEED = 1.5d;

    private Map<Integer, Zombie> zombies; // May need IntHashMap.

    public ZombieManager() {
        this.zombies = new HashMap<Integer, Zombie>();
    }

    public void add(Location spawn) {
        Zombie z = new Zombie(spawn);
        this.zombies.put(z.getId(), z);
    }

    public void addMany(Location center, int count) {
        for (int i = 0; i < count; i++) {
            this.add(center.add((Math.random() - 0.5) * (count / 2),
                    (Math.random() - 0.5) * (count / 2), (Math.random() - 0.5)
                            * (count / 2)));
        }
    }

    public void remove(Entity e) {
        Zombie removed = this.zombies.remove(e.getEntityId());
        removed.destroy();
    }
}
