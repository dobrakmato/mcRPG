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

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.Starving;

public class ChunkServer implements Runnable {

    public static final int LOAD_RADIUS = 4;

    private ChunkProvider provider;

    public ChunkServer(World world) {
        this.provider = new ChunkProvider(world);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(
                Starving.getInstance().getPlugin(), this, 0L, 1L);
    }

    @Override
    public void run() {
        this.tick();
    }

    private void tick() {
        // Compute loaded chunks.
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        IntStack loadedChunks = new IntStack(
                (int) (players.size() * 2 * Math.pow(
                        LOAD_RADIUS * 2, 2)));
        for (Player p : players) {
            this.addLoadedChunks(loadedChunks, p);
        }
        // Load computed, unload others.
        int x, z;
        boolean isLoaded;
        while (!loadedChunks.isEmpty()) {
            z = loadedChunks.pop();
            x = loadedChunks.pop();
            isLoaded = this.provider.isLoaded(x, z);
            if (!isLoaded) {
                this.provider.loadChunk(x, z);
            }
        }
    }

    private void addLoadedChunks(IntStack coll, Player p) {
        Location loc = p.getLocation();
        int x = (int) (loc.getX() % 16);
        int z = (int) (loc.getZ() % 16);

        push(coll, x, z);
        for (int i = -LOAD_RADIUS; i < LOAD_RADIUS; i++) {
            for (int j = -LOAD_RADIUS; j < LOAD_RADIUS; j++) {
                push(coll, x + i, z + j);
            }
        }
    }

    private void push(IntStack coll, int x, int z) {
        coll.push(x);
        coll.push(z);
    }

    private void loadChunk(int x, int z) {

    }

}
