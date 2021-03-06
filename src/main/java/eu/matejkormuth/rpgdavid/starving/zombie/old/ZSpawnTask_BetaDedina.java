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

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import eu.matejkormuth.rpgdavid.starving.Region;
import eu.matejkormuth.rpgdavid.starving.tasks.RepeatingTask;

public class ZSpawnTask_BetaDedina extends RepeatingTask {
    private World world = Bukkit.getWorld("Beta");
    private Vector regionStart = new Vector(558, 25, -269);
    private Vector regionEnd = new Vector(758, 70, -69);
    private Region region = new Region(regionStart, regionEnd, world);
    private Random random = new Random();

    @Override
    public void run() {
        if (this.world == null) {
            this.world = Bukkit.getWorld("Beta");
        }

        if (Bukkit.getOnlinePlayers().size() < 1) {
            return;
        }

        int zcount = countZombies();
        int target = 30;
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
        int x = this.region.getMinXFloor() + random.nextInt(200);
        int z = this.region.getMinZFloor() + random.nextInt(200);

        int y = findY(x, z);
        if (y == -1) {
            return null;
        }
        // int y = 80;

        return new Location(world, x, y, z);
    }

    private int findY(int x, int z) {
        int i = region.getMinYFloor();
        Block b = null;
        for (; i < 255; i++) {
            b = world.getBlockAt(x, i, z);
            if (b.getType() == Material.GRASS) {
                return i + 2;
            }
        }
        return -1;
    }

    private int countZombies() {
        return world.getEntitiesByClasses(org.bukkit.entity.Zombie.class).size();
    }
}
