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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class BufferedImpulseProcessor implements ImpulseProcessor {
    // Max hear distance = 50 blocks.
    private static final double MAX_HEAR_DISTANCE_SQUARED = 25 * 25;
    // Max power = 1.0d. (1.0 can be heard in 25 blocks radius).
    private static final double MAX_POWER = 1;

    private List<Imp> buffer;
    private TargetProvider provider;

    public BufferedImpulseProcessor() {
        this.buffer = new ArrayList<BufferedImpulseProcessor.Imp>();
    }

    @Override
    public void impulse(Location location, float power) {
        if (power > MAX_POWER) {
            throw new IllegalArgumentException("power");
        }

        this.buffer.add(new Imp(location, power));
    }

    public void process() {
        // Do not allow more pushes to buffer.
        synchronized (this.buffer) {
            // Declare locals.
            ImpulseTarget target = null;
            double dSq;
            float cPower;
            // Get targets.
            List<ImpulseTarget> targets = this.provider.getTargets();
            // Finally applied impulses.
            Imp[] applied = new Imp[targets.size()];
            for (int i = 0; i < targets.size(); i++) {
                target = targets.get(i);
                if (target.canReceiveImpulse()) {
                    // Check each impulse
                    for (Imp imp : this.buffer) {
                        // Squared distance from impulse to target.
                        dSq = distSquared(imp, target);
                        // If can this target hear this impulse.
                        if (this.distSquared(imp, target) < MAX_HEAR_DISTANCE_SQUARED) {
                            // Get power of impulse at target's location.
                            cPower = (float) (dSq / MAX_HEAR_DISTANCE_SQUARED
                                    * imp.power / MAX_POWER);
                            if (applied[i] == null) {
                                applied[i] = new Imp(imp.loc, cPower);
                            } else {
                                if (applied[i].power < cPower) {
                                    applied[i].power = cPower;
                                }
                            }
                        }
                    }
                }
            }
            // Apply to targets.
            for (int i = 0; i < targets.size(); i++) {
                target = targets.get(i);
                if (applied[i] != null) {
                    target.onImpulse(applied[i].loc, applied[i].power);
                }
            }
            // Clear buffer.
            this.buffer.clear();
        }
    }

    private double distSquared(final Imp imp, final ImpulseTarget target) {
        return Math.pow(imp.loc.getX() - target.getLocation().getX(), 2)
                + Math.pow(imp.loc.getY() - target.getLocation().getY(), 2)
                + Math.pow(imp.loc.getZ() - target.getLocation().getZ(), 2);
    }

    private class Imp {
        public Location loc;
        public float power;

        public Imp(Location loc, float power) {
            this.loc = loc;
            this.power = power;
        }
    }
}
