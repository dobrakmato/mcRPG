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
package eu.matejkormuth.rpgdavid.starving;

import java.io.File;
import java.util.logging.Logger;

import net.minecraft.server.v1_8_R1.PacketPlayOutNamedSoundEffect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;
import eu.matejkormuth.rpgdavid.starving.items.ItemsManager;
import eu.matejkormuth.rpgdavid.starving.listeners.ZombieListener;
import eu.matejkormuth.rpgdavid.starving.persistence.PersistInjector;
import eu.matejkormuth.rpgdavid.starving.sounds.AmbientSoundManager;
import eu.matejkormuth.rpgdavid.starving.tasks.LocalityTeller;
import eu.matejkormuth.rpgdavid.starving.zombie.ZombieManager;

public class Starving implements Runnable {
    private static Starving instance;

    public static Starving getInstance() {
        if (instance == null) {
            instance = new Starving();
        }
        return instance;
    }

    private Starving() {
    }

    private Logger log;
    private File dataFolder;
    private ZombieManager zombieManager;
    private AmbientSoundManager ambientSoundManager;
    private Plugin plugin;
    private ItemsManager itemsManager;

    public void onEnable() {
        instance = this;

        this.log = Logger.getLogger("Starving");
        this.log.setParent(RpgPlugin.getInstance().getLogger());

        this.plugin = RpgPlugin.getInstance();

        this.dataFolder = new File(RpgPlugin.getInstance().getDataFolder()
                .getParent()
                + "/Starving/");
        this.dataFolder.mkdirs();

        // Set game rules.
        this.getLogger().info("Setting starving game rules...");
        for (World w : Bukkit.getWorlds()) {
            w.setGameRuleValue("doMobSpawning", "false");
        }

        // Initialize PersistInjector
        File confDirectory = new File(this.dataFolder.getAbsolutePath()
                + "/conf/");
        confDirectory.mkdirs();
        PersistInjector
                .setConfigurationsFolder(confDirectory.getAbsolutePath());

        // Initialize all managers.
        this.zombieManager = new ZombieManager();
        this.ambientSoundManager = new AmbientSoundManager();
        this.itemsManager = new ItemsManager();

        // Schedule all tasks.
        new LocalityTeller().schedule(20L);

        // Register starving listeners.
        Bukkit.getPluginManager().registerEvents(new ZombieListener(),
                RpgPlugin.getInstance());

        // Register starving repeating tasks.
        Bukkit.getScheduler().scheduleSyncRepeatingTask(
                RpgPlugin.getInstance(), this, 0L, 1L);
    }

    public void onDisable() {
        this.zombieManager.saveConfiguration();
    }

    public void run() {
        // Tick.
    }

    public Logger getLogger() {
        return this.log;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public ZombieManager getZombieManager() {
        return this.zombieManager;
    }

    public ItemsManager getItemsManager() {
        return this.itemsManager;
    }

    public AmbientSoundManager getAmbientSoundManager() {
        return ambientSoundManager;
    }

    @NMSHooks(version = "v1_8_R1")
    public static final class NMS {
        public static final void playNamedSoundEffectGlobally(Player player,
                String soundEffectName, Location location) {
            ((CraftPlayer) player).getHandle().playerConnection
                    .sendPacket(new PacketPlayOutNamedSoundEffect(
                            soundEffectName, location.getX(), location.getY(),
                            location.getZ(), Float.MAX_VALUE, 1));
        }

        public static final void playNamedSoundEffect(Player player,
                String soundEffectName, Location location, float volume,
                float pitch) {
            ((CraftPlayer) player).getHandle().playerConnection
                    .sendPacket(new PacketPlayOutNamedSoundEffect(
                            soundEffectName, location.getX(), location.getY(),
                            location.getZ(), volume, pitch));
        }
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public Locality getLocality(final Location location) {
        return Locality.WILDERNESS;
    }
}
