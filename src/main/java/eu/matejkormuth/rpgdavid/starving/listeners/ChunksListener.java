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
package eu.matejkormuth.rpgdavid.starving.listeners;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.google.common.io.Files;

import eu.matejkormuth.rpgdavid.starving.Starving;

public class ChunksListener implements Listener {
    @EventHandler
    private void onChunkLoad(final ChunkLoadEvent event) {

    }

    @EventHandler
    private void onChunkUnload(final ChunkUnloadEvent event) {
        StringBuilder builder = new StringBuilder();
        String fileName = event.getChunk().getX() + "_"
                + event.getChunk().getZ() + ".zombies";
        for (Entity e : event.getChunk().getEntities()) {
            if (e instanceof Zombie) {
                Location loc = e.getLocation();
                builder.append(loc.getX());
                builder.append(',');
                builder.append(loc.getY());
                builder.append(',');
                builder.append(loc.getZ());
                builder.append('\n');
                e.remove();
            }
        }

        try {
            Files.write(builder.toString().getBytes(), new File(Starving
                    .getInstance().getDataFolder().getAbsolutePath()
                    + "/chunkdata/" + fileName));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
