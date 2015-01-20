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

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.World;
import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;
import eu.matejkormuth.rpgdavid.starving.npc.path.Path;

@NMSHooks(version = "v1_8_R1")
public class PlayerNPC extends EntityHuman implements NPC {

    public PlayerNPC(World world, GameProfile gameprofile) {
        super(world, gameprofile);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void teleport(Location location) {
        // TODO Auto-generated method stub

    }

    @Override
    public void teleport(Location location, TeleportCause cause) {
        // TODO Auto-generated method stub

    }

    @Override
    public void lookAt(Location location) {
        // TODO Auto-generated method stub

    }

    @Override
    public void lookAt(Entity entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setLook(float pitch, float yaw) {
        // TODO Auto-generated method stub

    }

    @Override
    public void walkTo(Location location) {
        // TODO Auto-generated method stub

    }

    @Override
    public void walkBy(Path path) {
        // TODO Auto-generated method stub

    }

    @Override
    public void follow(Entity entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public void despawn() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean v() {
        // TODO Auto-generated method stub
        return false;
    }

}
