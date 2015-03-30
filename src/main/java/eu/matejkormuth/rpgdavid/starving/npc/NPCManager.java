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

import eu.matejkormuth.rpgdavid.starving.tasks.RepeatingTask;

public class NPCManager {

    private Map<String, NPCRegistry> registers;
    private NPCRegistry mainRegistry;
    private NPCProfiler profiler;
    private RepeatingTask task;

    public NPCManager() {
        this.registers = new HashMap<>();
        this.profiler = new NPCProfiler();
        this.mainRegistry = new NPCRegistry("main", this.profiler);

        this.task = new RepeatingTask() {
            @Override
            public void run() {
                NPCManager.this.updateAllRegisters();
            }
        };
        this.task.schedule(1);
    }

    private void updateAllRegisters() {
        this.mainRegistry.tick();
    }

    public NPCProfiler getProfiler() {
        return profiler;
    }

    public NPCRegistry getMainRegistry() {
        return mainRegistry;
    }

    public NPCRegistry getCustomRegistry(String name) {
        if (registers.containsKey(name)) {
            return registers.get(name);
        } else {
            return registers.put(name, new NPCRegistry(name, this.profiler));
        }
    }

    public void shutdown() {
        this.task.cancel();
    }
}
