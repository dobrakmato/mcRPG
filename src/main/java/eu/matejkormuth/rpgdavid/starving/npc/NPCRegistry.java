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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

import com.mojang.authlib.GameProfile;

import eu.matejkormuth.rpgdavid.starving.npc.behaviours.base.AbstractBehaviour;
import eu.matejkormuth.rpgdavid.starving.npc.types.HumanNPC;

public class NPCRegistry {

    private final Map<Integer, NPC> entityByIds;
    private final String name;
    public final NPCProfiler profiler;

    public NPCRegistry(String name, NPCProfiler profiler) {
        this.entityByIds = new HashMap<>();
        this.name = name;
        this.profiler = profiler;
    }

    public String getName() {
        return name;
    }

    public NPC getNPC(int entityId) {
        return entityByIds.get(entityId);
    }

    public PlayerNPCBuilder createPlayer() {
        return new PlayerNPCBuilder();
    }

    protected void addNPC(NPC npc) {
        this.entityByIds.put(npc.getId(), npc);
    }

    protected void removeNPC(NPC npc) {
        this.entityByIds.remove(npc.getId());
    }

    public void tick() {
        for (NPC npc : entityByIds.values()) {
            npc.tickEntity();
        }
    }

    public class PlayerNPCBuilder {
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

        public PlayerNPCBuilder withSpawnLocation(Location spawnLocation) {
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

        public NPC spawn() {
            if (this.spawn == null) {
                throw new NullPointerException(
                        "Spawn location can't be null. You must set spawn location.");
            }
            return buildPlayerNpc();

        }

        private NPC buildPlayerNpc() {
            HumanNPC npc = new HumanNPC(NPCRegistry.this,
                    this.profile, this.spawn);
            NPCRegistry.this.addNPC(npc);
            return npc;
        }
    }

}
