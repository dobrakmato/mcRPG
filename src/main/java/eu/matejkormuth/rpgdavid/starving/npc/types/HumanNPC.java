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
package eu.matejkormuth.rpgdavid.starving.npc.types;

import java.io.IOException;
import java.net.Socket;

import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.EnumGamemode;
import net.minecraft.server.v1_8_R1.EnumProtocolDirection;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.PlayerInteractManager;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftInventoryPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.PlayerInventory;

import com.mojang.authlib.GameProfile;

import eu.matejkormuth.rpgdavid.starving.Debug;
import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;
import eu.matejkormuth.rpgdavid.starving.npc.NPC;
import eu.matejkormuth.rpgdavid.starving.npc.NPCRegistry;
import eu.matejkormuth.rpgdavid.starving.npc.K;
import eu.matejkormuth.rpgdavid.starving.npc.behaviours.base.AbstractBehaviour;
import eu.matejkormuth.rpgdavid.starving.npc.behaviours.base.BehaviourHolder;
import eu.matejkormuth.rpgdavid.starving.npc.util.NullNetworkManager;
import eu.matejkormuth.rpgdavid.starving.npc.util.NullPlayerConnection;
import eu.matejkormuth.rpgdavid.starving.npc.util.NullSocket;

@NMSHooks(version = "v1_8_R1")
public class HumanNPC extends EntityPlayer implements NPC {

    // Reference of NPCRegistry of this NPC.
    private NPCRegistry registry;
    // Behaviour holder.
    private BehaviourHolder holder;
    // Whether this entity is active or not.
    private boolean active = true;

    public HumanNPC(NPCRegistry registry, GameProfile gameprofile,
            Location spawnLocation) {
        super(MinecraftServer.getServer(), ((CraftWorld) spawnLocation
                .getWorld()).getHandle(), gameprofile,
                new PlayerInteractManager(((CraftWorld) spawnLocation
                        .getWorld()).getHandle()));
        // Set NPCRegisrty.
        this.registry = registry;

        // Replace playerConnection with custom connection.
        try {
            Socket socket = new NullSocket();
            NetworkManager conn = new NullNetworkManager(
                    EnumProtocolDirection.CLIENTBOUND);
            this.playerConnection = new NullPlayerConnection(MinecraftServer
                    .getServer(),
                    conn, this);
            conn.a(playerConnection);
            socket.close();
        } catch (IOException e) {
        }
        // Allow step climbing.
        this.S = 1;
        // Set default gamemode.
        this.playerInteractManager.setGameMode(EnumGamemode.SURVIVAL);

        // Create behaviour holder.
        this.holder = new BehaviourHolder();

        // Add to world.
        this.setLocation(spawnLocation.getX(), spawnLocation.getY(),
                spawnLocation.getZ(), spawnLocation.getYaw(), spawnLocation
                        .getPitch());
        world.addEntity(this);

    }

    // Override vanilla methods.
    @Override
    protected void doTick() {
        // Tick update is in tickEntity();
    }
    
    public void tickEntity() {
        this.registry.profiler.start(K.NPC_DO_TICK);
        // Sets head rotation to yaw;
        this.aI = this.yaw;
        this.registry.profiler.start(K.NPC_BEHAVIOURS);
        this.holder.tick();
        this.registry.profiler.start(K.NPC_BEHAVIOURS);
        this.registry.profiler.end(K.NPC_DO_TICK);
    }

    // Some extensional methods.
    public void setInvulnerable(boolean invulnerable) {
        this.abilities.isInvulnerable = invulnerable;
    }

    public void setFlying(boolean flying) {
        this.abilities.isFlying = true;
    }

    public boolean isFlying() {
        return this.abilities.isFlying;
    }

    public PlayerInventory getInventory() {
        return new CraftInventoryPlayer(this.inventory);
    }

    // Behaviour delegate methods.
    public <T extends AbstractBehaviour> T getBehaviour(Class<T> type) {
        return holder.getBehaviour(type);
    }

    public boolean hasBehaviour(Class<? extends AbstractBehaviour> type) {
        return holder.hasBehaviour(type);
    }

    public void addBehaviour(AbstractBehaviour abstractBehaviour) {
        holder.addBehaviour(abstractBehaviour);
    }

    // Other NPC methods.
    @Override
    public Location getLocation() {
        return new Location(this.world.getWorld(), this.lastX, this.lastY,
                this.lastZ, this.yaw, this.pitch);
    }

    @Override
    public boolean hasLineOfSight(LivingEntity e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void setActive(boolean active) {
        if (active) {
            Debug.log(ChatColor.GRAY + "NPC " + this.getUniqueID()
                    + " is now activated!");
        } else {
            Debug.log(ChatColor.DARK_GRAY + "NPC " + this.getUniqueID()
                    + " is now deactivated!");
        }
        this.active = active;
    }

    @Override
    public void teleport(Location location) {
        teleport(location, TeleportCause.PLUGIN);
    }

    @Override
    public void teleport(Location location, TeleportCause cause) {
        if ((this.passenger != null) || (this.dead)) {
            throw new RuntimeException(
                    "Can't teleport HumanNPC because it is dead or has passanger on it.");
        }

        this.mount(null);

        if (!(location.getWorld().equals(getWorld()))) {
            this.teleportTo(location, cause
                    .equals(TeleportCause.NETHER_PORTAL));
            return;
        }

        this.setLocation(location.getX(), location.getY(), location
                .getZ(), location.getYaw(), location.getPitch());
    }

    @Override
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    @Override
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    @Override
    public void setRotation(float yaw, float pitch) {
        this.setYawPitch(yaw, pitch);
    }

    @Override
    public boolean v() {
        return false;
    }

    @Override
    public NPCRegistry getRegistry() {
        return this.registry;
    }

    @Override
    public void remove0() {
        this.dead = true;
        // TODO: Inform NPCRegistry about dead.
    }
}
