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

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import eu.matejkormuth.bukkit.Worlds;
import eu.matejkormuth.rpgdavid.starving.Region;
import eu.matejkormuth.rpgdavid.starving.tasks.RepeatingTask;

public class TempZombieManager extends RepeatingTask {
    private World world = Worlds.by("Beta");
    private Vector regionStart = new Vector(558, 25, -269);
    private Vector regionEnd = new Vector(758, 70, -69);
    private Region region = new Region(regionStart, regionEnd, world);
    private Random random = new Random();

    @Override
    public void run() {
        int zcount = countZombies();
        int target = 100;
        int needed = target - zcount;
        for (int i = 0; i < needed; i++) {
            spawn();
        }
    }

    private void spawn() {
        Location loc = randLoc();
        if (loc != null) {
            world.spawnEntity(loc, EntityType.ZOMBIE);
        }
    }

    private Location randLoc() {
        int x = 0;
        int z = 0;
        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                x = this.region.getMinXFloor() + random.nextInt(10);
                z = this.region.getMinZFloor() + random.nextInt(10);
            } else {
                x = this.region.getMaxXFloor() - random.nextInt(10);
                z = this.region.getMaxZFloor() - random.nextInt(10);
            }
        } else {
            if (random.nextBoolean()) {
                x = this.region.getMinXFloor() + random.nextInt(10);
                z = this.region.getMaxZFloor() - random.nextInt(10);
            } else {
                x = this.region.getMaxXFloor() - random.nextInt(10);
                z = this.region.getMinZFloor() + random.nextInt(10);
            }
        }

        int y = findY(x, z);
        if (y == -1) {
            return null;
        }

        return new Location(world, x, y, z);
    }

    private int findY(int x, int z) {
        int i = region.getMinYFloor();
        Block b = null;
        for (; i < 255; i++) {
            b = world.getBlockAt(x, i, z);
            if (b.getType() == Material.GRASS) {
                return i + 1;
            }
        }
        return -1;
    }

    private int countZombies() {
        int count = 0;
        for (Entity z : world.getEntitiesByClasses(Zombie.class)) {
            if (region.isInside(z.getLocation().toVector())) {
                count++;
            }
        }
        return count;
    }
}
