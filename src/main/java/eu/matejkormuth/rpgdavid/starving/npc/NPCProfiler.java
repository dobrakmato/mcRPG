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
package eu.matejkormuth.rpgdavid.starving.npc;

import java.util.HashMap;
import java.util.Map;

public final class NPCProfiler {

    private Map<String, ProfilerEntry> entries;

    public NPCProfiler() {
        this.entries = new HashMap<>(256);
    }

    public final void start(final String key) {
        if(this.entries.containsKey(key)) {
            
        } else {
            
        }
    }

    public final void end(final String key) {
        
    }

    public static final class ProfilerEntry {
        private final String key;
        private long count;
        private double totalTime;

        public ProfilerEntry(final String key) {
            this.key = key;
        }

        public final void add(double totalTime) {
            this.totalTime += totalTime;
            this.count++;
        }

        public final long getCount() {
            return count;
        }

        public final String getKey() {
            return key;
        }

        public final double getTotalTime() {
            return totalTime;
        }

        public final double getAvarageTime() {
            return totalTime / (double) count;
        }
    }
}
