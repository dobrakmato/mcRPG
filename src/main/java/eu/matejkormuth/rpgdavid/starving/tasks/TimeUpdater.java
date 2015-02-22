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

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class TimeUpdater extends RepeatingTask {
    private final List<World> worlds;
    private long fullTime;

    public TimeUpdater() {
        // Cache worlds for better performance and memory usage.
        this.worlds = Bukkit.getWorlds();
        this.fullTime = this.worlds.get(0).getFullTime();
    }

    @Override
    public void run() {
        // Increment time.
        this.fullTime++;
        this.setTime();
    }

    private void setTime() {
        for (World w : this.worlds) {
            w.setFullTime(this.fullTime);
        }
    }

    public long getFullTime() {
        return this.fullTime;
    }

    public void vanllaSetMoveTime(long time) {
        for (World w : this.worlds) {
            w.setTime(time);
        }
        this.fullTime = this.worlds.get(0).getFullTime();
    }
}
