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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import com.darkblade12.particleeffect.ParticleEffect;

import eu.matejkormuth.rpgdavid.starving.rockets.collisions.CollisionSolver;
import eu.matejkormuth.rpgdavid.starving.rockets.collisions.StepInterpolationInBlockCollisionSolver;

public class Rocket {

    private static final float ROCKET_PARTICLES_RANDOMDIR_COEF = 0.1f;
    private static final float VELOCITY_MUL = 2;
    private static final ItemStack ROCKET_ITEM = new ItemStack(
            Material.YELLOW_FLOWER);
    private static final int MAX_LIFETIME = 20 * 10;
    private static final int MAX_Y = 384;
    private static final double EXPLOSION_RANGE = 4;

    // Collision solver used by this rocket.
    private static CollisionSolver collisionSolver = new StepInterpolationInBlockCollisionSolver(
            (int) VELOCITY_MUL);

    private Location loc;
    private Location lastLoc;
    private Vector vel;
    private Player shooter;
    private ArmorStand rocket;
    private int lifetime;
    private boolean dead = false;

    public Rocket(Player shooter, Location loc, Vector direction) {
        this.shooter = shooter;
        this.loc = loc;
        this.lastLoc = loc.subtract(0.5, 0.5, 0.5);
        this.vel = direction.normalize().multiply(VELOCITY_MUL);

        // Clear pitch and yaw from location.
        loc.setYaw(0);
        loc.setPitch(0);

        this.rocket = (ArmorStand) loc.getWorld().spawnEntity(loc,
                EntityType.ARMOR_STAND);
        this.rocket.setGravity(false);
        this.rocket.setBasePlate(false);
        this.rocket.setVisible(false);
        this.rocket.setHelmet(ROCKET_ITEM);
        // Use head pose for rocket rotation.
        this.rocket.setHeadPose(new EulerAngle(direction.getX(),
                direction.getY(), direction.getZ()));

        // Play launch sound effect.
        this.loc.getWorld().playSound(this.loc, Sound.FIREWORK_LAUNCH, 1, 0);
    }

    public void update() {
        this.lifetime++;

        // Check for possible causes of rocket removing.
        if (this.lifetime > MAX_LIFETIME) {
            this.remove();
        }

        if (this.loc.getY() > MAX_Y) {
            this.remove();
        }

        // Check for collisions.
        if (collisionSolver.collided(this.lastLoc, this.loc)) {
            this.explode();
            this.remove();
        }

        // Move the rocket.
        this.lastLoc = loc.clone();
        this.loc.add(this.vel);
        this.rocket.teleport(this.loc);

        // Make rocket effect.
        Vector dir = this.vel.clone().multiply(-1);
        for (int i = 0; i < 20; i++) {
            // Display engine particles.
            double offsetX = (Math.random() - 0.5f)
                    * ROCKET_PARTICLES_RANDOMDIR_COEF;
            double offsetY = (Math.random() - 0.5f)
                    * ROCKET_PARTICLES_RANDOMDIR_COEF;
            double offsetZ = (Math.random() - 0.5f)
                    * ROCKET_PARTICLES_RANDOMDIR_COEF;
            ParticleEffect.FIREWORKS_SPARK.display(dir.add(new Vector(offsetX,
                    offsetY, offsetZ)), 0.5f, this.loc,
                    Double.MAX_VALUE);
        }
    }

    private void explode() {

        // Play sound and make effect.
        this.loc.getWorld().playSound(this.loc, Sound.EXPLODE, 1, 1);
        ParticleEffect.EXPLOSION_NORMAL.display(1, 1, 1, 0.5f, 200, this.loc,
                Double.MAX_VALUE);
        ParticleEffect.SMOKE_LARGE.display(1, 1, 1, 0.5f, 80, this.loc,
                Double.MAX_VALUE);

        // Apply damage to shooter and make him jump.
        double dist = this.loc.distance(shooter.getLocation());
        if (dist <= EXPLOSION_RANGE) {
            if (shooter.isSneaking()) {

                if (shooter.getEyeLocation().getPitch() > 75) {
                    Vector shooterVel = shooter.getVelocity();
                    if (shooterVel.getX() != 0 || shooterVel.getY() != 0) {
                        shooter.setVelocity(shooterVel.normalize().multiply(
                                new Vector(dist / 1.33f,
                                        (EXPLOSION_RANGE - dist) / 1.33f,
                                        dist / 1.33f)));
                    }
                } else {
                    // Rocket jump.
                    shooter.setVelocity(shooter.getLocation()
                            .subtract(this.loc)
                            .toVector()
                            .normalize()
                            .multiply(
                                    new Vector(dist / 1.33f,
                                            (EXPLOSION_RANGE - dist) / 1.33f,
                                            dist / 1.33f)));
                }

            } else {
                // Not rocket jump.
                shooter.setVelocity(shooter.getLocation().subtract(this.loc).toVector().normalize());
                shooter.damage(1D);
            }
        }

        // Damage and throw away nearby players.
        for (Entity e : this.rocket.getNearbyEntities(EXPLOSION_RANGE,
                EXPLOSION_RANGE, EXPLOSION_RANGE)) {

            if (e == shooter) {
                continue;
            }

            if (e.getLocation().distanceSquared(this.loc) <= EXPLOSION_RANGE
                    * EXPLOSION_RANGE * 2) {
                if (e instanceof LivingEntity && !(e instanceof ArmorStand)) {
                    e.setVelocity(e.getLocation().subtract(this.loc).toVector().normalize());
                    ((LivingEntity) e).damage(Math.abs(EXPLOSION_RANGE * 1.414)
                            - this.loc.distance(e.getLocation())
                            * 2D);
                }
            }
        }
    }

    private void remove() {
        this.rocket.remove();
        // Mark rocket as dead so RocketUniverse can remove it on next
        // iteration.
        this.dead = true;
    }

    public boolean isDead() {
        return this.dead;
    }
}
