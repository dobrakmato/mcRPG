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
package eu.matejkormuth.rpgdavid.starving.worldgen;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import eu.matejkormuth.bukkit.Worlds;
import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.worldgen.accessors.BukkitWorldAccessor;

public class WorldGenManager {

    private Map<Player, PlayerSession> sessions;
    private Map<World, WorldAccessor> worlds;

    private Constructor<? extends WorldAccessor> preferedAccessorCtr;

    public WorldGenManager() {
        sessions = new WeakHashMap<>();
        worlds = new WeakHashMap<>();

        // Determinate ideal accessor for this server.
        determinateAccessor();

        // Register wand listener.
        Bukkit.getPluginManager().registerEvents(new WandListener(this),
                Starving.getInstance().getPlugin());
    }

    private void determinateAccessor() {
        Class<? extends WorldAccessor> clazz = BukkitWorldAccessor.class;
        try {
            preferedAccessorCtr = clazz.getConstructor(World.class);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    private WorldAccessor createAccessor(World world) {
        try {
            return preferedAccessorCtr.newInstance(new Object[] { world });
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public PlayerSession getSession(Player player) {
        if (!sessions.containsKey(player)) {
            sessions.put(player, new PlayerSession(player));
        }

        return this.sessions.get(player);
    }

    public WorldAccessor getWorld(String name) {
        return getWorld(Worlds.by(name));
    }

    public WorldAccessor getWorld(World world) {
        if (!worlds.containsKey(world)) {
            worlds.put(world, createAccessor(world));
        }

        return worlds.get(world);
    }

}
