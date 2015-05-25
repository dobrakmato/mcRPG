/**
 * Rocket Madness - Rocket jumping Minecraft minigame.
 * Copyright (c) 2015, Matej Kormuth <http://www.github.com/dobrakmato>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package eu.matejkormuth.rpgdavid.starving.rockets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;

import eu.matejkormuth.rpgdavid.starving.Starving;

public class RocketUniverse {
    // List of rockets in this universe.
    private List<Rocket> rockets;
    // Id of Bukkit task that this universe uses for updating rockets.
    private int taskId = -1;

    public RocketUniverse() {
        this.rockets = new ArrayList<Rocket>();
    }

    public void startSimulation() {
        if (taskId != -1 && taskId != -2) {
            throw new RuntimeException("Simulation has been alredy started!");
        }

        // Schedule repeating task.
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(
                Starving.getInstance().getPlugin(),
                new Runnable() {
                    public void run() {
                        // Update rockets each tick.
                        updateRockets();
                    }
                }, 1L, 1L);
    }

    public boolean isTimePassing() {
        return this.taskId != -1;
    }
    
    public void stopSimulation() {
        if (taskId == -1) {
            throw new RuntimeException("Simulation has not been yet started!");
        }

        if (taskId == -2) {
            throw new RuntimeException("Simulation has been alredy stopped!");
        }

        Bukkit.getScheduler().cancelTask(this.taskId);
        this.taskId = -2;
    }

    public void addRocket(Rocket rocket) {
        this.rockets.add(rocket);
    }

    private void updateRockets() {
        for (Iterator<Rocket> iterator = this.rockets.iterator(); iterator.hasNext();) {
            Rocket r = iterator.next();

            // Remove dead rockets.
            if (r.isDead()) {
                iterator.remove();
                continue;
            }

            // Otherwise update the rocket.
            r.update();
        }
    }
}
