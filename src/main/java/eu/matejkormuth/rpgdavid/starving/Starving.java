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
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.ChatMessage;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R1.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import net.minecraft.server.v1_8_R1.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R1.PacketPlayOutResourcePackSend;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PacketPlayOutUpdateTime;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;
import eu.matejkormuth.rpgdavid.starving.commands.RpCommandExecutor;
import eu.matejkormuth.rpgdavid.starving.commands.SetSpeedCommandExecutor;
import eu.matejkormuth.rpgdavid.starving.commands.SetWarpCommandExecutor;
import eu.matejkormuth.rpgdavid.starving.commands.WarpCommandExecutor;
import eu.matejkormuth.rpgdavid.starving.impulses.BufferedImpulseProcessor;
import eu.matejkormuth.rpgdavid.starving.impulses.ImpulseProcessor;
import eu.matejkormuth.rpgdavid.starving.items.ItemManager;
import eu.matejkormuth.rpgdavid.starving.listeners.AnimalDropsListener;
import eu.matejkormuth.rpgdavid.starving.listeners.BlockFadeListener;
import eu.matejkormuth.rpgdavid.starving.listeners.BloodLevelDamageListener;
import eu.matejkormuth.rpgdavid.starving.listeners.ChatListener;
import eu.matejkormuth.rpgdavid.starving.listeners.ChunksListener;
import eu.matejkormuth.rpgdavid.starving.listeners.ExperiencePointsListener;
import eu.matejkormuth.rpgdavid.starving.listeners.ExplosionListener;
import eu.matejkormuth.rpgdavid.starving.listeners.FractureListener;
import eu.matejkormuth.rpgdavid.starving.listeners.HeadshotListener;
import eu.matejkormuth.rpgdavid.starving.listeners.HiddenCommandsListener;
import eu.matejkormuth.rpgdavid.starving.listeners.LootListener;
import eu.matejkormuth.rpgdavid.starving.listeners.MobDropsListener;
import eu.matejkormuth.rpgdavid.starving.listeners.MoveListener;
import eu.matejkormuth.rpgdavid.starving.listeners.PVPListener;
import eu.matejkormuth.rpgdavid.starving.listeners.PlayerDeathListener;
import eu.matejkormuth.rpgdavid.starving.listeners.ProjectileListener;
import eu.matejkormuth.rpgdavid.starving.listeners.TabListListener;
import eu.matejkormuth.rpgdavid.starving.listeners.ToolsListener;
import eu.matejkormuth.rpgdavid.starving.listeners.VersionListener;
import eu.matejkormuth.rpgdavid.starving.listeners.ZombieCombustListener;
import eu.matejkormuth.rpgdavid.starving.listeners.ZombieListener;
import eu.matejkormuth.rpgdavid.starving.npc.NPCManager;
import eu.matejkormuth.rpgdavid.starving.persistence.AbstractPersistable;
import eu.matejkormuth.rpgdavid.starving.persistence.PersistInjector;
import eu.matejkormuth.rpgdavid.starving.persistence.Persistable;
import eu.matejkormuth.rpgdavid.starving.remote.RemoteServer;
import eu.matejkormuth.rpgdavid.starving.sounds.AmbientSoundManager;
import eu.matejkormuth.rpgdavid.starving.tasks.BleedingTask;
import eu.matejkormuth.rpgdavid.starving.tasks.BloodLevelConsuquencesTask;
import eu.matejkormuth.rpgdavid.starving.tasks.BloodReplenishTask;
import eu.matejkormuth.rpgdavid.starving.tasks.BodyTemperatureUpdater;
import eu.matejkormuth.rpgdavid.starving.tasks.FlashlightTask;
import eu.matejkormuth.rpgdavid.starving.tasks.HallucinationsTask;
import eu.matejkormuth.rpgdavid.starving.tasks.HydrationDepletionTask;
import eu.matejkormuth.rpgdavid.starving.tasks.HydrationLevelConsequencesTask;
import eu.matejkormuth.rpgdavid.starving.tasks.LocalityTeller;
import eu.matejkormuth.rpgdavid.starving.tasks.ScoreboardUpdater;
import eu.matejkormuth.rpgdavid.starving.tasks.StaminaRegenerationTask;
import eu.matejkormuth.rpgdavid.starving.tasks.TablistFooterClockTask;
import eu.matejkormuth.rpgdavid.starving.tasks.TimeUpdater;
import eu.matejkormuth.rpgdavid.starving.zombie.ZombieManager;

public class Starving implements Runnable, Listener {

    // Ticks elapsed since server start.
    public static AtomicLong ticksElapsed = new AtomicLong();

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
    private NPCManager npcManager;

    private List<Persistable> persistablesList;

    private Configuration warpsConfig;

    private String tabListHeader = "Welcome to &cStarving 2.0!";
    private String tabListFooter = "http://www.starving.eu";

    private List<Object> registered;

    private RemoteServer remoteServer;

    public void onEnable() {
        instance = this;

        this.persistablesList = new ArrayList<>();
        this.registered = new ArrayList<>();

        // Initialize logger to special StarvingLogger.
        this.log = new StarvingLogger();
        this.log.setParent(RpgPlugin.getInstance()
                .getLogger());

        this.corePlugin = RpgPlugin.getInstance();

        this.dataFolder = new File(RpgPlugin.getInstance()
                .getDataFolder()
                .getParent()
                + "/Starving/");
        this.dataFolder.mkdirs();

        // Load configurations.
        this.warpsConfig = new Configuration(this.getFile("warps.yml"));

        // Set game rules.
        this.getLogger()
                .info("Setting starving game rules...");
        for (World w : Bukkit.getWorlds()) {
            w.setGameRuleValue("doMobSpawning", "false");
        }

        // Initialize PersistInjector
        File confDirectory = new File(this.dataFolder.getAbsolutePath()
                + "/conf/");
        confDirectory.mkdirs();
        new File(this.dataFolder.getAbsolutePath() + "/pdatas/").mkdirs();
        new File(this.dataFolder.getAbsolutePath() + "/chunkdata/").mkdirs();
        PersistInjector
                .setConfigurationsFolder(confDirectory.getAbsolutePath());

        // Initialize all managers.
        this.zombieManager = new ZombieManager();
        this.ambientSoundManager = new AmbientSoundManager();
        this.itemManager = new ItemManager();

        this.impulseProcessor = new BufferedImpulseProcessor();

        this.npcManager = new NPCManager();

        // Register all command executors.
        this.getPlugin()
                .getCommand("warp")
                .setExecutor(new WarpCommandExecutor());
        this.getPlugin()
                .getCommand("setwarp")
                .setExecutor(new SetWarpCommandExecutor());
        this.getPlugin()
                .getCommand("setspeed")
                .setExecutor(new SetSpeedCommandExecutor());
        this.getPlugin()
                .getCommand("rp")
                .setExecutor(new RpCommandExecutor());

        // Schedule all tasks.
        this.register(new BleedingTask())
                .schedule(1L);
        this.register(new FlashlightTask())
                .schedule(1L);
        this.register(new TimeUpdater())
                .schedule(2L);
        // TablistFooterClockTask MUST be registered after TimeUpdater.
        this.register(new TablistFooterClockTask())
                .schedule(5L);
        this.register(new LocalityTeller())
                .schedule(20L);
        this.register(new BodyTemperatureUpdater())
                .schedule(20L);
        this.register(new StaminaRegenerationTask())
                .schedule(20L);
        this.register(new BloodLevelConsuquencesTask())
                .schedule(20L);
        this.register(new HydrationDepletionTask())
                .schedule(20L);
        this.register(new HydrationLevelConsequencesTask())
                .schedule(20L);
        this.register(new BloodReplenishTask())
                .schedule(20L);
        this.register(new ScoreboardUpdater())
                .schedule(20L);
        this.register(new HallucinationsTask())
                .schedule(200L);

        // Register starving listeners.
        this.register(new ZombieListener());
        this.register(new AnimalDropsListener());
        this.register(new HeadshotListener());
        this.register(new ChatListener());
        this.register(new LootListener());
        this.register(new TabListListener());
        this.register(new PVPListener());
        this.register(new VersionListener());
        this.register(new MoveListener());
        this.register(new ExplosionListener());
        this.register(new FractureListener());
        this.register(new MobDropsListener());
        this.register(new ToolsListener());
        this.register(new ChunksListener());
        this.register(new BlockFadeListener());
        this.register(new ZombieCombustListener());
        this.register(new ProjectileListener());
        this.register(new BloodLevelDamageListener());
        this.register(new ExperiencePointsListener());
        this.register(new PlayerDeathListener());
        // Register commands listener.
        this.register(new HiddenCommandsListener());
        // Register this as listener.
        this.register(this);

        // Register starving repeating tasks.
        Bukkit.getScheduler()
                .scheduleSyncRepeatingTask(
                        RpgPlugin.getInstance(), this, 0L, 1L);

        // Print some useful info.
        this.printImplementations();

        // Patch server.
        new ServerZombiePatcher().patchAll();

        // Start remote connections.
        this.remoteServer = new RemoteServer();
        this.remoteServer.start();
    }

    /**
     * Proxy method to allow easier registration of Listeners and to be sure
     * that {@link AbstractPersistable}s, are saved when plugin's being
     * disabled.
     * 
     * This metod returns passed object.
     * 
     * @return passed object
     */
    private <T> T register(T object) {
        // Register all.
        this.registered.add(object);

        if (object instanceof Listener) {
            this.getLogger()
                    .info(
                            " New listener: " + object.getClass()
                                    .getName());
            Bukkit.getPluginManager()
                    .registerEvents((Listener) object,
                            this.corePlugin);
        }

        if (object instanceof Persistable) {
            this.getLogger()
                    .info(
                            " New Persistable: " + object.getClass()
                                    .getName());
            this.persistablesList.add((Persistable) object);
        }

        return object;
    }

    private void printImplementations() {
        this.getLogger()
                .info("Using following implementations:");
        this.getLogger()
                .info(
                        " ImpulseProcessor: "
                                + this.impulseProcessor.getClass()
                                        .getName());
        this.getLogger()
                .info(
                        " ZombieManager: " + this.zombieManager.getClass()
                                .getName());
        this.getLogger()
                .info(
                        " aSoundManager: "
                                + this.ambientSoundManager.getClass()
                                        .getName());
    }

    public void onDisable() {
        this.zombieManager.saveConfiguration();

        // Stop remote server.
        this.remoteServer.stop();

        // Shutdown NPC manager.
        this.getLogger()
                .info("Shutting down NPCManager...");
        this.npcManager.shutdown();

        // Save configurations.
        this.warpsConfig.save();

        // Save all cached Data-s.
        for (Data d : Data.cached()) {
            d.save()
                    .uncache();
        }

        DataDefaults.get()
                .saveConfiguration();

        // Save configuration of all persistables.
        for (Persistable persistable : this.persistablesList) {
            persistable.saveConfiguration();
        }
    }

    public void run() {
        // Tick.
        ticksElapsed.incrementAndGet();
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
        for (Player p : Bukkit.getOnlinePlayers()) {
            NMS.setPlayerListHeaderFooter(p, this.tabListHeader,
                    this.tabListFooter);
        }
    }

    public void setTabListHeader(String tabListHeader) {
        this.tabListHeader = tabListHeader;
        for (Player p : Bukkit.getOnlinePlayers()) {
            NMS.setPlayerListHeaderFooter(p, this.tabListHeader,
                    this.tabListFooter);
        }
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

    public NPCManager getNPCManager() {
        return this.npcManager;
    }

    public AmbientSoundManager getAmbientSoundManager() {
        return this.ambientSoundManager;
    }

    @EventHandler
    private void onPlayerJoin(final PlayerJoinEvent event) {
        // Read data and send resource pack.
        String rp = Data.of(event.getPlayer())
                .getResourcePack();
        if (rp.equalsIgnoreCase("builders")) {
            event.getPlayer()
                    .setResourcePack("http://www.starving.eu/2/rp/latest.zip");
        } else {
            event.getPlayer()
                    .setResourcePack(
                            "http://www.starving.eu/2/rp/latest_builder.zip");
        }
    }

    @EventHandler
    private void onPlayerDisconnect(final PlayerQuitEvent event) {
        // Save data of the player.
        Data.of(event.getPlayer())
                .uncache()
                .save();
    }

    @EventHandler
    private void onPlayerRespawn(final PlayerRespawnEvent event) {
        // TODO: Load last savepoint instead.
        event.getPlayer()
                .sendMessage(
                        ChatColor.YELLOW + "Data has been reseted!");
        Data.of(event.getPlayer())
                .reset();
    }

    public JavaPlugin getPlugin() {
        return (JavaPlugin) this.corePlugin;
    }

    public Locality getLocality(final Location location) {
        return Locality.WILDERNESS;
    }

    public boolean isDebug() {
        return true;
    }

    @SuppressWarnings("unchecked")
    public <T> T getRegistered(Class<T> clazz) {
        for (Object o : this.registered) {
            if (clazz.isInstance(o)) {
                return (T) o;
            }
        }
        return null;
    }

    @NMSHooks(version = "v1_8_R1")
    public static final class NMS {
        public static final void playNamedSoundEffectMaxVolume(Player player,
                String soundEffectName, Location location) {
            sendPacket(player, new PacketPlayOutNamedSoundEffect(
                    soundEffectName, location.getX(), location.getY(),
                    location.getZ(), Float.MAX_VALUE, 1));
        }

        public static final void sendResourcePack(Player player, String url,
                String sha1lower40chars) {
            sendPacket(player, new PacketPlayOutResourcePackSend(url,
                    sha1lower40chars));
        }

        public static final void displayMaterialBreak(Location loc) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getLocation()
                        .distanceSquared(loc) < 16384) {
                    sendPacket(p, new PacketPlayOutBlockBreakAnimation(
                            Starving.getInstance().random.nextInt(),
                            new BlockPosition(loc.getBlockX(), loc
                                    .getBlockY(),
                                    loc.getBlockZ()),
                            Starving.getInstance()
                                    .getRandom()
                                    .nextInt(2) + 4));
                }
            }
        }

        public static final void displayBloodEffects(Location loc) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getLocation()
                        .distanceSquared(loc) < 16384) {
                    sendPacket(p, new PacketPlayOutBlockBreakAnimation(
                            Starving.getInstance().random.nextInt(),
                            new BlockPosition(loc.getBlockX(), loc
                                    .getBlockY(),
                                    loc.getBlockZ()),
                            Starving.getInstance()
                                    .getRandom()
                                    .nextInt(2) + 1));
                }
            }
        }

        public static final void playNamedSoundEffect(Player player,
                String soundEffectName, Location location, float volume,
                float pitch) {
            sendPacket(player, new PacketPlayOutNamedSoundEffect(
                    soundEffectName, location.getX(), location.getY(),
                    location.getZ(), volume, pitch));
        }

        public static final void setPlayerListHeaderFooter(Player player,
                String header, String footer) {
            IChatBaseComponent hj = ChatSerializer.a("{'text':'"
                    + header.replace("&", "§") + "'}");
            IChatBaseComponent fj = ChatSerializer.a("{'text':'"
                    + footer.replace("&", "§") + "'}");
            PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
            try {
                Field headerField = packet.getClass()
                        .getDeclaredField("a");
                headerField.setAccessible(true);
                headerField.set(packet, hj);
                headerField.setAccessible(!headerField.isAccessible());

                Field footerField = packet.getClass()
                        .getDeclaredField("b");
                footerField.setAccessible(true);
                footerField.set(packet, fj);
                footerField.setAccessible(!headerField.isAccessible());

            } catch (Exception e) {
                e.printStackTrace();
            }
            sendPacket(player, packet);
        }

        public static final void updateTime(Player player, long time) {
            sendPacket(player, new PacketPlayOutUpdateTime(time, time, true));
        }

        public static final void sendTitle(Player p, Locality loc, int fadeIn,
                int fadeOut, int stay) {
            PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(
                    EnumTitleAction.TITLE, new ChatMessage(loc.getName()),
                    fadeIn, stay, fadeOut);
            sendPacket(p, titlePacket);
        }

        public static final void sendAboveActionBarMessage(Player player,
                String message) {
            sendPacket(player, new PacketPlayOutChat(new ChatMessage(message),
                    (byte) 2));
        }

        public static void sendAnimation(Entity entity, int animationId) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getLocation()
                        .distanceSquared(entity.getLocation()) < 1024) {
                    sendPacket(player, new PacketPlayOutAnimation(
                            ((CraftEntity) entity).getHandle(),
                            animationId));
                }
            }
        }

        public static void sendAnimation(
                net.minecraft.server.v1_8_R1.Entity entity, int animationId) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getLocation()
                        .distanceSquared(
                                entity.getBukkitEntity()
                                        .getLocation()) < 1024) {
                    sendPacket(player, new PacketPlayOutAnimation(entity,
                            animationId));
                }
            }
        }

        public static net.minecraft.server.v1_8_R1.World getNMSWorld(World world) {
            return ((CraftWorld) world).getHandle();
        }

        public static void sendPacket(Player player,
                Packet packet) {
            ((CraftPlayer) player).getHandle().playerConnection
                    .sendPacket(packet);
        }
    }

    public void setWarp(String name, Location location) {
        this.warpsConfig.set(name + ".x", location.getX());
        this.warpsConfig.set(name + ".y", location.getY());
        this.warpsConfig.set(name + ".z", location.getZ());
        this.warpsConfig.set(name + ".pitch", location.getPitch());
        this.warpsConfig.set(name + ".yaw", location.getYaw());
        this.warpsConfig.set(name + ".world", location.getWorld()
                .getName());
    }

    public boolean isWarp(String name) {
        return this.warpsConfig.contains(name + ".x");
    }

    public Location getWarp(String name) {
        double x = this.warpsConfig.getDouble(name + ".x");
        double y = this.warpsConfig.getDouble(name + ".y");
        double z = this.warpsConfig.getDouble(name + ".z");
        double pitch = this.warpsConfig.getDouble(name + ".pitch");
        double yaw = this.warpsConfig.getDouble(name + ".yaw");
        String wname = this.warpsConfig.getString(name + ".world");
        return new Location(Bukkit.getWorld(wname), x, y, z, (float) yaw,
                (float) pitch);
    }

    public File getFile(String string) {
        return new File(this.dataFolder.getAbsolutePath() + "/" + string);
    }
}
