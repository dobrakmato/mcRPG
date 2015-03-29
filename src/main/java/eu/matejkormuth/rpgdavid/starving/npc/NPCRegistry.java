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
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

import com.mojang.authlib.GameProfile;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.npc.behaviours.base.AbstractBehaviour;
import eu.matejkormuth.rpgdavid.starving.npc.types.HumanNPC;

public class NPCRegistry {

    public PlayerNPCBuilder createPlayer() {
        return new PlayerNPCBuilder();
    }

    public static class PlayerNPCBuilder {
        protected List<AbstractBehaviour> behaviours;
        protected Location spawn;
        private GameProfile profile;

        public PlayerNPCBuilder() {
            behaviours = new ArrayList<>();
        }

        public PlayerNPCBuilder withBehaviour(AbstractBehaviour behaviour) {
            behaviours.add(behaviour);
            return this;
        }

        public PlayerNPCBuilder withSpawn(Location spawnLocation) {
            this.spawn = spawnLocation;
            return this;
        }

        public PlayerNPCBuilder withProfile(UUID uuid, String name) {
            this.profile = new GameProfile(uuid, name);
            return this;
        }

        public PlayerNPCBuilder withProfile(String name) {
            this.profile = new GameProfile(UUID.randomUUID(), name);
            return this;
        }

        public NPC build() {
            if (this.spawn == null) {
                throw new NullPointerException(
                        "Spawn location can't be null. You must set spawn location.");
            }
            return buildPlayerNpc();

        }

        private NPC buildPlayerNpc() {
            return new HumanNPC(
                    Starving.NMS.getNMSWorld(this.spawn.getWorld()),
                    this.profile);
        }
    }

}
