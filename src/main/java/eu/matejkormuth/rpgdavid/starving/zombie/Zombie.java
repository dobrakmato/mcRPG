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
package eu.matejkormuth.rpgdavid.starving.zombie;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.TrigMath;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R1.util.UnsafeList;

import eu.matejkormuth.rpgdavid.bukkitfixes.FlagMetadataValue;
import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.DamageSource;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityZombie;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.World;

@NMSHooks(version = "v1_8_R1")
public class Zombie extends EntityZombie {
    private Entity following;
    private float followSpeed = 1.0f;
    private double followDistanceLimit = 2043f;

    private long lastJump = Long.MAX_VALUE;
    private boolean disabled;

    protected Zombie(final Location spawnLocation) {
        this(((CraftWorld) spawnLocation.getWorld()).getHandle());
        this.teleportTo(spawnLocation, true);
    }

    private Zombie(World world) {
        super(world);
        this.removeAI();
        this.getBukkitEntity().setMetadata("starving", new FlagMetadataValue());
    }

    @SuppressWarnings("rawtypes")
    private void removeAI() {
        try {
            Field b = PathfinderGoalSelector.class.getField("b");
            Field c = PathfinderGoalSelector.class.getField("c");
            b.set(this.goalSelector, new UnsafeList());
            c.set(this.goalSelector, new UnsafeList());
            b.set(this.targetSelector, new UnsafeList());
            c.set(this.targetSelector, new UnsafeList());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void K() {
        super.K();
        this.tick();
    }

    private void tick() {
        this.followTarget();
    }

    public void jump() {
        if (this.lastJump > System.currentTimeMillis() + 250) {
            if (this.onGround) {
                this.getControllerJump().a();
                this.lastJump = System.currentTimeMillis();
            }
        }
    }

    private void cancelNavigation(NavigationFailReason reason) {
        this.following = null;
        System.out.println("Navigation of " + this.getId() + " failed: "
                + reason.toString());
    }

    private void followTarget() {
        // Check if zombie lost sight.
        if (distanceToFollowing() > this.followDistanceLimit) {
            // Lost sight.
            this.cancelNavigation(NavigationFailReason.ENTITY_OUT_OF_SIGHT);
            return;
        }

        // Rotate head.
        this.yaw = -1
                * (float) (TrigMath.atan2(this.following.locX - this.locX,
                        this.following.locZ - this.locZ) * 180 / Math.PI);
        this.pitch = 0;
        // Calculate shift and next position.
        double dX = this.following.locX - this.locX;
        double dZ = this.following.locZ - this.locZ;
        double dLength = Math.sqrt(dX * dX + dZ * dZ);
        dX = dX / dLength * this.followSpeed;
        dZ = dZ / dLength * this.followSpeed;
        double nextX = this.locX + dX;
        double nextZ = this.locZ + dZ;

        // If stuck in ground, jump.
        if (this.world.getWorld()
                .getBlockAt((int) this.locX, (int) this.locY, (int) this.locZ)
                .getType().isSolid()) {
            this.getControllerJump().a();
        }

        // Don't walk into a block.
        if (this.world.getWorld()
                .getBlockAt((int) nextX, (int) this.locY, (int) nextZ)
                .getType().isSolid()) {
            // If it is a wall, we can't jump over it, navigation fail.
            if (this.world
                    .getWorld()
                    .getBlockAt((int) nextX, (int) (this.locY + 1.5D),
                            (int) nextZ).getType().isSolid()) {
                this.cancelNavigation(NavigationFailReason.HIT_WALL);
            } else {
                // Jump over the block.
                this.jump();
            }
        }

        // If is zombie near player damage player.
        if (this.distanceToFollowing() < 1.5F) {
            this.following.damageEntity(DamageSource.mobAttack(this),
                    (float) Math.random() * 2);
        }

        // Update position.
        this.setPositionRotation(new BlockPosition(nextX, this.locY, nextZ),
                yaw, pitch);
        // Update head rotation.
        this.aI = yaw;
        this.positionChanged = true;
    }

    private double distanceToFollowing() {
        return Math.pow((this.locX - this.following.locX), 2)
                + Math.pow((this.locY - this.following.locY), 2)
                + Math.pow((this.locZ - this.following.locZ), 2);
    }

    public Entity getFollowingTarget() {
        return this.following;
    }

    public float getFollowSpeed() {
        return followSpeed;
    }

    public void setFollowSpeed(float followSpeed) {
        this.followSpeed = followSpeed;
    }

    public void setFollowTarget(final org.bukkit.entity.Entity e) {
        this.following = ((CraftEntity) e).getHandle();
    }

    public static enum NavigationFailReason {
        ENTITY_OUT_OF_SIGHT, HIT_WALL
    }

    public void destroy() {
        this.following = null;
        this.dead = true;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void teleport(Location location) {
        this.getBukkitEntity().teleport(location);
    }
}
