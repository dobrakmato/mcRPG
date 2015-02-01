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
package eu.matejkormuth.rpgdavid.starving.impulses;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Location;

public abstract class BufferedImpulseProcessor implements ImpulseProcessor {
    private Queue<Imp> buffer;
    private TargetProvider provider;

    public BufferedImpulseProcessor() {
        this.buffer = new LinkedList<BufferedImpulseProcessor.Imp>();
    }

    @Override
    public void impulse(Location location, float power) {
        this.buffer.add(new Imp(location, power));
    }

    public void process() {
        for (ImpulseTarget target : this.provider.getTargets()) {
            if (target.isActive()) {
                Location loc = target.getLocation();
            }
        }
    }

    private Collection<Imp> getNear(ImpulseTarget target, float near) {
        Imp imp = null;
        while ((imp = this.buffer.poll()) != null) {
            return null;
        }
        return null;
    }

    private class Imp {
        private Location loc;
        private float power;

        public Imp(Location loc, float power) {
            this.loc = loc;
            this.power = power;
        }
    }
}
