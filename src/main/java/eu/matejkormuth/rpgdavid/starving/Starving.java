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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import net.minecraft.server.v1_8_R1.ChatMessage;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import net.minecraft.server.v1_8_R1.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PacketPlayOutUpdateTime;
import net.minecraft.server.v1_8_R1.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;
import eu.matejkormuth.rpgdavid.starving.impulses.BufferedImpulseProcessor;
import eu.matejkormuth.rpgdavid.starving.impulses.ImpulseProcessor;
import eu.matejkormuth.rpgdavid.starving.items.ItemManager;
import eu.matejkormuth.rpgdavid.starving.listeners.BloodLevelDamageListener;
import eu.matejkormuth.rpgdavid.starving.listeners.ChatListener;
import eu.matejkormuth.rpgdavid.starving.listeners.ExperiencePointsListener;
import eu.matejkormuth.rpgdavid.starving.listeners.FractureListener;
import eu.matejkormuth.rpgdavid.starving.listeners.HeadshotListener;
import eu.matejkormuth.rpgdavid.starving.listeners.HiddenCommandsListener;
import eu.matejkormuth.rpgdavid.starving.listeners.LootListener;
import eu.matejkormuth.rpgdavid.starving.listeners.TabListListener;
import eu.matejkormuth.rpgdavid.starving.listeners.ZombieListener;
import eu.matejkormuth.rpgdavid.starving.persistence.IPersistable;
import eu.matejkormuth.rpgdavid.starving.persistence.PersistInjector;
import eu.matejkormuth.rpgdavid.starving.persistence.Persistable;
import eu.matejkormuth.rpgdavid.starving.sounds.AmbientSoundManager;
import eu.matejkormuth.rpgdavid.starving.tasks.BleedingTask;
import eu.matejkormuth.rpgdavid.starving.tasks.BloodLevelConsuquencesTask;
import eu.matejkormuth.rpgdavid.starving.tasks.BodyTemperatureUpdater;
import eu.matejkormuth.rpgdavid.starving.tasks.LocalityTeller;
import eu.matejkormuth.rpgdavid.starving.tasks.ScoreboardUpdater;
import eu.matejkormuth.rpgdavid.starving.tasks.StaminaRegenerationTask;
import eu.matejkormuth.rpgdavid.starving.tasks.HydrationDepletionTask;
import eu.matejkormuth.rpgdavid.starving.tasks.TimeUpdater;
import eu.matejkormuth.rpgdavid.starving.zombie.ZombieManager;

public class Starving implements Runnable, Listener {
    private static Starving instance;

    public static Starving getInstance() {
        if (instance == null) {
            instance = new Starving();
        }
        return instance;
    }

    private Starving() {
    }

    private Random random = new Random();

    private Logger log;
    private File dataFolder;
    private ZombieManager zombieManager;
    private AmbientSoundManager ambientSoundManager;
    private Plugin corePlugin;
    private ItemManager itemManager;
    private ImpulseProcessor impulseProcessor;

    private List<IPersistable> persistablesList;

    private String tabListHeader = "Welcome to &cStarving 2.0!";
    private String tabListFooter = "http://www.starving.eu";

    public void onEnable() {
        instance = this;

        this.persistablesList = new ArrayList<>();

        // Initialize logger to special StarvingLogger.
        this.log = new StarvingLogger();
        this.log.setParent(RpgPlugin.getInstance().getLogger());

        this.corePlugin = RpgPlugin.getInstance();

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
        new File(this.dataFolder.getAbsolutePath() + "/pdatas/").mkdirs();
        PersistInjector
                .setConfigurationsFolder(confDirectory.getAbsolutePath());

        // Initialize all managers.
        this.zombieManager = new ZombieManager();
        this.ambientSoundManager = new AmbientSoundManager();
        this.itemManager = new ItemManager();

        this.impulseProcessor = new BufferedImpulseProcessor();

        // Schedule all tasks.
        this.register(new BleedingTask()).schedule(1L);
        this.register(new TimeUpdater()).schedule(2L);
        this.register(new LocalityTeller()).schedule(20L);
        this.register(new BodyTemperatureUpdater()).schedule(20L);
        this.register(new StaminaRegenerationTask()).schedule(20L);
        this.register(new BloodLevelConsuquencesTask()).schedule(20L);
        this.register(new HydrationDepletionTask()).schedule(20L);
        this.register(new ScoreboardUpdater()).schedule(20L);

        // Register starving listeners.
        this.register(new ZombieListener());
        this.register(new HeadshotListener());
        this.register(new ChatListener());
        this.register(new LootListener());
        this.register(new TabListListener());
        this.register(new FractureListener());
        this.register(new BloodLevelDamageListener());
        this.register(new ExperiencePointsListener());
        // Register commands listener.
        this.register(new HiddenCommandsListener());
        // Register this as listener.
        this.register(this);

        // Register starving repeating tasks.
        Bukkit.getScheduler().scheduleSyncRepeatingTask(
                RpgPlugin.getInstance(), this, 0L, 1L);

        // Print some useful info.
        this.printImplementations();
    }

    /**
     * Proxy method to allow easier registration of Listeners and to be sure
     * that {@link Persistable}s, are saved when plugin's being disabled.
     * 
     * This metod returns passed object.
     * 
     * @return passed object
     */
    private <T> T register(T object) {
        if (object instanceof Listener) {
            this.getLogger().info(
                    " New listener: " + object.getClass().getName());
            Bukkit.getPluginManager().registerEvents((Listener) object,
                    this.corePlugin);
        }

        if (object instanceof IPersistable) {
            this.getLogger().info(
                    " New IPersistable: " + object.getClass().getName());
            this.persistablesList.add((IPersistable) object);
        }

        return object;
    }

    private void printImplementations() {
        this.getLogger().info("Using following implementations:");
        this.getLogger().info(
                " ImpulseProcessor: "
                        + this.impulseProcessor.getClass().getName());
        this.getLogger().info(
                " ZombieManager: " + this.zombieManager.getClass().getName());
        this.getLogger().info(
                " aSoundManager: "
                        + this.ambientSoundManager.getClass().getName());
    }

    public void onDisable() {
        this.zombieManager.saveConfiguration();

        // Save all cached Data-s.
        for (Data d : Data.cached()) {
            d.save().uncache();
        }

        DataDefaults.get().saveConfiguration();

        // Save configuration of all persistables.
        for (IPersistable persistable : this.persistablesList) {
            persistable.saveConfiguration();
        }
    }

    public void run() {
        // Tick.
    }

    public Random getRandom() {
        return this.random;
    }

    public String getTabListFooter() {
        return this.tabListFooter;
    }

    public String getTabListHeader() {
        return this.tabListHeader;
    }

    public void setTabListFooter(String tabListFooter) {
        this.tabListFooter = tabListFooter;
    }

    public void setTabListHeader(String tabListHeader) {
        this.tabListHeader = tabListHeader;
    }

    public Logger getLogger() {
        return this.log;
    }

    public ImpulseProcessor getImpulseProcessor() {
        return this.impulseProcessor;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public ZombieManager getZombieManager() {
        return this.zombieManager;
    }

    public ItemManager getItemManager() {
        return this.itemManager;
    }

    public AmbientSoundManager getAmbientSoundManager() {
        return ambientSoundManager;
    }

    @EventHandler
    private void onPlayerDisconnect(final PlayerQuitEvent event) {
        // Save data of the player.
        Data.of(event.getPlayer()).uncache().save();
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

        public static final void setPlayerListHeaderFooter(Player player,
                String header, String footer) {
            CraftPlayer cplayer = (CraftPlayer) player;
            PlayerConnection connection = cplayer.getHandle().playerConnection;
            IChatBaseComponent hj = ChatSerializer.a("{'text':'"
                    + header.replace("&", "§") + "'}");
            IChatBaseComponent fj = ChatSerializer.a("{'text':'"
                    + footer.replace("&", "§") + "'}");
            PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
            try {
                Field headerField = packet.getClass().getDeclaredField("a");
                headerField.setAccessible(true);
                headerField.set(packet, hj);
                headerField.setAccessible(!headerField.isAccessible());

                Field footerField = packet.getClass().getDeclaredField("b");
                footerField.setAccessible(true);
                footerField.set(packet, fj);
                footerField.setAccessible(!headerField.isAccessible());

            } catch (Exception e) {
                e.printStackTrace();
            }
            connection.sendPacket(packet);
        }

        public static final void updateTime(Player player, long time) {
            ((CraftPlayer) player).getHandle().playerConnection
                    .sendPacket(new PacketPlayOutUpdateTime(time, time, true));
        }

        public static final void sendTitle(Player p, Locality loc, int fadeIn,
                int fadeOut, int stay) {
            PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(
                    EnumTitleAction.TITLE, new ChatMessage(loc.getName()),
                    fadeIn, stay, fadeOut);
            ((CraftPlayer) p).getHandle().playerConnection
                    .sendPacket(titlePacket);
        }

        public static final void sendAboveActionBarMessage(Player player,
                String message) {
            ((CraftPlayer) player).getHandle().playerConnection
                    .sendPacket(new PacketPlayOutChat(new ChatMessage(message),
                            (byte) 2));
        }
    }

    public Plugin getPlugin() {
        return this.corePlugin;
    }

    public Locality getLocality(final Location location) {
        return Locality.WILDERNESS;
    }
}
