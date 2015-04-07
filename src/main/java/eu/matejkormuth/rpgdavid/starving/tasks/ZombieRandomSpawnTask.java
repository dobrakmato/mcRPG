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
package eu.matejkormuth.rpgdavid.starving.tasks;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.EntityZombie;
import net.minecraft.server.v1_8_R2.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import eu.matejkormuth.bukkit.Worlds;
import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;
import eu.matejkormuth.rpgdavid.starving.persistence.Persist;

@NMSHooks(version = "v1_8_R2")
public class ZombieRandomSpawnTask extends RepeatingTask {

    /**
     * Set of invalid spawn materials.
     */
    private static final Set<Material> INVALID_SPAWN_MATERIALS = new HashSet<>(
            Arrays.asList(Material.LEAVES, Material.LEAVES_2));

    @Persist(key = "zombiesPerPlayer")
    private int zombiesPerPlayer;

    // World this task is being performed on.
    private WorldServer world = ((CraftWorld) Worlds.first()).getHandle();

    private int renderDistance = 4 * 16;

    private Random random = new Random();

    @Override
    public void run() {
        int playerCount = Bukkit.getOnlinePlayers().size();
        int zombieCount = this.countZombies();

        int targetCount = playerCount * zombiesPerPlayer;
        if (targetCount > zombieCount) {
            // We need more zombies.
            spawnZombies(targetCount - zombieCount, playerCount);
        } else {
            // We need less zombies.
            // TODO: Do something to lower the values of zombies.
        }
    }

    private void spawnZombies(int k, int pc) {
        int spawned = 0;
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        while (true) {
            for (Player p : players) {
                if (spawned >= k) {
                    return;
                }

                // Spawn one.
                if (this.spawnNear(p)) {
                    spawned++;
                }
            }
        }
    }

    private boolean spawnNear(Player p) {
        Block validLoc = null;
        int tries = 0;
        while (!isValidSpawnBlock(validLoc)) {
            if (tries > 3) {
                // Cancel spawning if we failed three times.
                return false;
            }
            validLoc = findLocation(p).getBlock();
        }

        spawnOne(validLoc.getLocation());
        return true;
    }

    private Location findLocation(Player p) {
        int randomX = random.nextInt(this.renderDistance * 2) / 2;
        int randomZ = random.nextInt(this.renderDistance * 2) / 2;
        int highestY = this.world.getHighestBlockYAt(
                new BlockPosition(randomX, 0, randomZ)).getY();
        return new Location(this.world.getWorld(), randomX, highestY, randomZ);
    }

    private boolean isValidSpawnBlock(Block block) {
        if (block == null) {
            return false;
        }
        return !INVALID_SPAWN_MATERIALS.contains(block.getType());
    }

    private void spawnOne(Location loc) {
        this.world.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
    }

    private int countZombies() {
        int count = 0;
        for (int i = 0; i < this.world.entityList.size(); i++) {
            if (this.world.entityList.get(i) instanceof EntityZombie) {
                count++;
            }
        }
        return count;
    }

}
