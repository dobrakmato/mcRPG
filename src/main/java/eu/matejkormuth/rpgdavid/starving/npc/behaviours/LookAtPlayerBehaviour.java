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
package eu.matejkormuth.rpgdavid.starving.npc.behaviours;

import org.bukkit.craftbukkit.v1_8_R1.TrigMath;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.npc.behaviours.base.AbstractBehaviour;
import eu.matejkormuth.rpgdavid.starving.persistence.Persist;

public class LookAtPlayerBehaviour extends AbstractBehaviour {

    @Persist(key = "MAX_FOLLOW_DIST")
    private static final double MAX_FOLLOW_DIST = 8;
    private static final double MAX_FOLLOW_DIST_SQUARED = MAX_FOLLOW_DIST
            * MAX_FOLLOW_DIST;
    private Player target;

    @Override
    public void tick(long currentTick) {
        if (currentTick % 10 == 0) {
            checkDistance();
            findTarget();
        }
        rotate();
    }

    private void checkDistance() {
        if (target != null) {
            if (this.owner.getLocation().distanceSquared(target.getLocation()) > MAX_FOLLOW_DIST_SQUARED) {
                this.target = null;
            }
        }
    }

    private void findTarget() {
        if (target == null) {
            for (Player p : this.owner.getNearbyPlayers(MAX_FOLLOW_DIST)) {
                if (this.owner.hasLineOfSight(p)) {
                    this.target = p;
                }
            }
        }
    }

    private void rotate() {
        float yaw = -1
                * (float) (TrigMath.atan2(target.getLocation().getX()
                        - this.owner.getLocation().getX(), target.getLocation()
                        .getZ()
                        - this.owner.getLocation().getZ()) * 180 / Math.PI);
        this.owner.setYaw(yaw);
    }

}
