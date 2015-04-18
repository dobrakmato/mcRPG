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
package eu.matejkormuth.rpgdavid.starving.particles;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import eu.matejkormuth.rpgdavid.starving.tasks.RepeatingTask;

public class ParticleEmitters extends RepeatingTask {

    private List<ParticleEmitter> emitters;

    public ParticleEmitters() {
        this.emitters = new ArrayList<ParticleEmitter>();
    }

    private void tick() {
        Chunk chunk; 
        for (ParticleEmitter pe : emitters) {
            if(pe.getLocation().getWorld() == null) {
                pe.getLocation().setWorld(Bukkit.getWorld("Beta"));
            }
            chunk = pe.getLocation().getChunk();
            if (chunk != null && chunk.isLoaded()) {
                pe.emit();
            }
        }
    }

    @Override
    public void run() {
        this.tick();
    }

    public void add(ParticleEmitter particleEmitter) {
        this.emitters.add(particleEmitter);
    }
    
    public void clear() {
        this.emitters.clear();
    }
}
