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

/**
 * <p>
 * This is simple collision solver based on Bukkit's Location.getBlock()
 * function.
 * </p>
 * <p>
 * Collision checking is done by returning whether the block at entity's current
 * location is solid or not.
 * </p>
 * <p>
 * <b>This is sufficient only for velocities (shifts) smaller than 1</b>, so the
 * {@link #collided(Location, Location)} method gets called at least one time
 * for each block in entity's route. For higher velocities, use different
 * solver.
 * </p>
 * 
 * <p>
 * This solver doesn't make any use of entity's last location.
 * </p>
 * 
 * @see StepInterpolationInBlockCollisionSolver
 */
public class InBlockCollisionSolver implements CollisionSolver {

    public boolean collided(Location lastLoc, Location loc) {
        return loc.getBlock().getType().isSolid();
    }

}
