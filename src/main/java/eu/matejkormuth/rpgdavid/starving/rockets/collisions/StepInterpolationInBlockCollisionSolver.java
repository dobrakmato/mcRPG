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
package eu.matejkormuth.rpgdavid.starving.rockets.collisions;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * <p>
 * This collision solver works almost the same way as
 * {@link InBlockCollisionSolver}.
 * </p>
 * <p>
 * Difference is that this solver interpolates between entity's last location
 * and new location by the specified amount of steps. This allows checking for
 * collision with each block in entity's route even when its velocity (shift) is
 * bigger then 1 block.
 * </p>
 * <p>
 * To get good result it's recommended to use at least same amount of steps as
 * entity's maximum velocity.
 * </p>
 * 
 * @see InBlockCollisionSolver
 */
public class StepInterpolationInBlockCollisionSolver implements CollisionSolver {

    private int subSteps;
    private Vector divisionVector;

    public StepInterpolationInBlockCollisionSolver(int subSteps) {
        this.subSteps = subSteps;
        this.divisionVector = new Vector(subSteps, subSteps, subSteps);
    }

    public boolean collided(Location start, Location end) {
        Vector step = end.clone().subtract(start).toVector().divide(divisionVector);

        for (int i = 0; i < subSteps; i++) {
            start.add(step);

            if (start.getBlock().getType().isSolid()) {
                return true;
            }
        }
        return false;
    }
}
