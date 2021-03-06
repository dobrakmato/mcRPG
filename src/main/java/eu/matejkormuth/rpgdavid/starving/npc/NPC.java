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

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R2.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;
import eu.matejkormuth.rpgdavid.starving.npc.behaviours.base.AbstractBehaviour;

public interface NPC {
    boolean isActive();

    void setActive(boolean active);

    Location getLocation();

    void teleport(Location location);

    void teleport(Location location, TeleportCause cause);

    default void remove() {
        this.getRegistry().removeNPC(this);
        this.remove0();
    }

    void remove0();

    NPCRegistry getRegistry();

    boolean hasBehaviour(Class<? extends AbstractBehaviour> abstractBehaviour);

    <T extends AbstractBehaviour> T getBehaviour(
            Class<T> abstractBehaviour);

    void addBehaviour(AbstractBehaviour behaviour);

    default Collection<Player> getNearbyPlayers(double maxDistance) {
        ArrayList<Player> al = new ArrayList<>();
        Location l = this.getLocation();
        double squaredDistMax = maxDistance * maxDistance;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (l.distanceSquared(p.getLocation()) < squaredDistMax) {
                al.add(p);
            }
        }
        return al;
    }

    boolean hasLineOfSight(LivingEntity e);

    void setInvulnerable(boolean invulnerable);

    void setYaw(float yaw);

    void setPitch(float pitch);

    void setRotation(float yaw, float pitch);

    @NMSHooks(version = "v1_8_R2")
    default void spawnFor(Player player) {
        Starving.NMS.sendPacket(player, new PacketPlayOutPlayerInfo(
                EnumPlayerInfoAction.ADD_PLAYER,
                (EntityPlayer) this));
        Starving.NMS.sendPacket(player, new PacketPlayOutNamedEntitySpawn(
                (EntityHuman) this));
    }

    @NMSHooks(version = "v1_8_R2")
    default void despawnFor(Player player) {
        Starving.NMS.sendPacket(player, new PacketPlayOutPlayerInfo(
                EnumPlayerInfoAction.REMOVE_PLAYER,
                (EntityPlayer) this));
        Starving.NMS.sendPacket(player, new PacketPlayOutEntityDestroy(this
                .getId()));
    }

    int getId();
    
    void tickEntity();
}
