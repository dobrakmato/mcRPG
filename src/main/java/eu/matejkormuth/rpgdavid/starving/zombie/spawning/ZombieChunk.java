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
package eu.matejkormuth.rpgdavid.starving.zombie.spawning;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

public class ZombieChunk {
    public static final int SIZE_X = 16;
    public static final int SIZE_Y = 128;
    public static final int SIZE_Z = 16;

    private int x;
    private int z;
    private World bukkitWorld;
    private Chunk bukkitChunk;
    private boolean loaded;

    public ZombieChunk(World w, int x, int z) {
        this.initialize();
    }

    private void initialize() {
        bukkitChunk = bukkitWorld.getChunkAt(x, z);
    }

    public Chunk getBukkitChunk() {
        if (!loaded) {
            throw new RuntimeException("This ZombieChunk is not loaded!");
        }

        return bukkitWorld.getChunkAt(x, z);
    }

    public void removeAllZombies() {
        if (!loaded) {
            throw new RuntimeException("This ZombieChunk is not loaded!");
        }

        for (Entity e : this.bukkitChunk.getEntities()) {
            if (e.getType() == EntityType.ZOMBIE) {
                e.remove();
            }
        }
    }

    public void storeAllZombies() {
        if (!loaded) {
            throw new RuntimeException("This ZombieChunk is not loaded!");
        }
         
        // TODO: Implement.
    }

    public void loadAllZombies() {
        // TODO: Implement.
    }

    public int getZombieCount() {
        if (!loaded) {
            throw new RuntimeException("This ZombieChunk is not loaded!");
        }

        return this.countZombies();
    }

    private int countZombies() {
        int count = 0;
        for (Entity e : this.bukkitChunk.getEntities()) {
            if (e.getType() == EntityType.ZOMBIE) {
                count++;
            }
        }
        return count;
    }

    public void load() {
        this.loadAllZombies();
        
        
    }

    public void unload() {
        if (!loaded) {
            throw new RuntimeException("This ZombieChunk is not loaded!");
        }

        this.storeAllZombies();
        this.removeAllZombies();
        this.bukkitChunk = null;
        this.bukkitWorld = null;
    }

    public Zombie spawnZombie(int x, int y, int z) {
        return (Zombie) this.spawnEntity(x, y, z, EntityType.ZOMBIE);
    }

    public Entity spawnEntity(int x, int y, int z, EntityType type) {
        if (!loaded) {
            throw new RuntimeException("This ZombieChunk is not loaded!");
        }
        return bukkitWorld.spawnEntity(new Location(bukkitWorld, this.x * 16
                + x, y, this.z * 16 + z), type);
    }
}
