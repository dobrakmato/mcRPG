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
package eu.matejkormuth.rpgdavid.starving.listeners;

import net.minecraft.server.v1_8_R1.EntityZombie;
import net.minecraft.server.v1_8_R1.GenericAttributes;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.persistence.Persist;

public class ZombieListener implements Listener {

    @Persist(key = "ZOMBIE_FOLLOW_RANGE")
    private static float ZOMBIE_FOLLOW_RANGE = 32f;

    @Persist(key = "ZOMBIE_MIN_SPEED")
    private static float ZOMBIE_MIN_SPEED = 0.2899999988f;

    @Persist(key = "ZOMBIE_MAX_SPEED")
    private static float ZOMBIE_MAX_SPEED = 0.499999988f;

    private static float ZOMBIE_SPEED_DIFF = ZOMBIE_MAX_SPEED
            - ZOMBIE_MIN_SPEED;

    @EventHandler
    private void onChunkUnload(final ChunkUnloadEvent event) {
        for (Entity e : event.getChunk().getEntities()) {
            if (e.getType() == EntityType.ZOMBIE) {
                if (e.hasMetadata("starving")) {
                    Starving.getInstance().getZombieManager().remove(e);
                }
            }
        }
    }

    @EventHandler
    private void onZombieSpawn(final EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE) {
            EntityZombie ez = ((CraftZombie) event.getEntity()).getHandle();
            ez.getAttributeInstance(GenericAttributes.b).setValue(
                    ZOMBIE_FOLLOW_RANGE); // follow
            // range
            ez.getAttributeInstance(GenericAttributes.d).setValue(
                    ZOMBIE_MIN_SPEED + Math.random() * ZOMBIE_SPEED_DIFF); // movement
            // speeds
        }
    }
}
