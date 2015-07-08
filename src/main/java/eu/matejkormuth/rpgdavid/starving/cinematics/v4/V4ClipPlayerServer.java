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
package eu.matejkormuth.rpgdavid.starving.cinematics.v4;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.Time;
import eu.matejkormuth.rpgdavid.starving.cinematics.PlayerServer;

public class V4ClipPlayerServer implements Runnable, PlayerServer {

    private Set<V4ClipPlayer> players;

    public V4ClipPlayerServer() {
        this.players = new HashSet<V4ClipPlayer>();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(
                Starving.getInstance().getPlugin(),
                this, Time.ofSeconds(2).toLongTicks(),
                Time.ofTicks(1).toLongTicks());
    }

    @Override
    public void addClipPlayer(V4ClipPlayer player) {
        this.players.add(player);
    }

    @Override
    public void removeClipPlayer(V4ClipPlayer player) {
        this.players.remove(player);
    }

    @Override
    public void run() {
        this.tick();
    }

    private void tick() {
        for (V4ClipPlayer player : this.players) {
            if (player.isPlaying()) {
                player.nextFrame();
            }
        }
    }
}
