/*
 *  mcRPG is a open source rpg bukkit/spigot plugin.
 *  Copyright (C) 2015 Matej Kormuth 
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
package eu.matejkormuth.rpgdavid;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionType;

import eu.matejkormuth.rpgdavid.bukkitfixes.WorkingPotion;
import eu.matejkormuth.rpgdavid.commands.CharacterCommandExecutor;
import eu.matejkormuth.rpgdavid.commands.NoCommandExecutor;
import eu.matejkormuth.rpgdavid.commands.PartyCommandExecutor;
import eu.matejkormuth.rpgdavid.commands.PlayerHeadCommandExecutor;
import eu.matejkormuth.rpgdavid.commands.YesCommandExecutor;
import eu.matejkormuth.rpgdavid.inventorymenu.Action;
import eu.matejkormuth.rpgdavid.inventorymenu.InventoryMenu;
import eu.matejkormuth.rpgdavid.inventorymenu.InventoryMenuItem;
import eu.matejkormuth.rpgdavid.listeners.BookOfSpellsListener;
import eu.matejkormuth.rpgdavid.listeners.QuestsBookListener;
import eu.matejkormuth.rpgdavid.listeners.SpellsListener;
import eu.matejkormuth.rpgdavid.listeners.WeaponsListener;
import eu.matejkormuth.rpgdavid.listeners.characters.AdventurerListener;
import eu.matejkormuth.rpgdavid.listeners.characters.HunterListener;
import eu.matejkormuth.rpgdavid.listeners.characters.KnightListener;
import eu.matejkormuth.rpgdavid.listeners.characters.ModifiersListener;
import eu.matejkormuth.rpgdavid.listeners.characters.UndeadListener;
import eu.matejkormuth.rpgdavid.listeners.characters.VampireListener;
import eu.matejkormuth.rpgdavid.party.Party;
import eu.matejkormuth.rpgdavid.quests.QuestManager;

public class RpgPlugin extends JavaPlugin implements Listener {
    private static RpgPlugin instnace;

    public static RpgPlugin getInstance() {
        return instnace;
    }
    
    public static List<String> getStringList(List<?> list) {        
        ArrayList<String> al = new ArrayList<String>();
        for (Object o : list) {
            al.add(String.valueOf(o));
        }
        return al;
    }

    private Logger log;
    private String dataFolder;
    private Map<UUID, Profile> loadedProfiles;
    private QuestManager questManager;
    private ScoreboardsList scoreboardsList;
    private Cooldowns cooldowns;

    private InventoryMenu characterChooserMenu;

    @Override
    public void onEnable() {
        instnace = this;

        // Initialize fields.
        this.scoreboardsList = new ScoreboardsList();
        this.loadedProfiles = new HashMap<UUID, Profile>();
        this.cooldowns = new Cooldowns();

        this.log = this.getLogger();
        this.log.info("Prepearing characters...");

        this.dataFolder = this.getDataFolder().getAbsolutePath();

        // Create folders.
        this.expandDirectoryStructure();

        // Create character chooser inventory menu.
        this.createCharacterChooser();

        // Register commands.
        this.getCommand("playerhead").setExecutor(
                new PlayerHeadCommandExecutor());
        this.getCommand("party").setExecutor(new PartyCommandExecutor());
        this.getCommand("character")
                .setExecutor(new CharacterCommandExecutor());
        this.getCommand("yes").setExecutor(new YesCommandExecutor());
        this.getCommand("no").setExecutor(new NoCommandExecutor());

        // Register event handlers.
        Bukkit.getPluginManager().registerEvents(this, this);

        Bukkit.getPluginManager().registerEvents(new SpellsListener(), this);
        Bukkit.getPluginManager().registerEvents(new WeaponsListener(), this);

        Bukkit.getPluginManager().registerEvents(new ModifiersListener(), this);
        Bukkit.getPluginManager()
                .registerEvents(new AdventurerListener(), this);
        Bukkit.getPluginManager().registerEvents(new HunterListener(), this);
        Bukkit.getPluginManager().registerEvents(new UndeadListener(), this);
        Bukkit.getPluginManager().registerEvents(new KnightListener(), this);
        Bukkit.getPluginManager().registerEvents(new VampireListener(), this);

        Bukkit.getPluginManager().registerEvents(new BookOfSpellsListener(),
                this);
        Bukkit.getPluginManager()
                .registerEvents(new QuestsBookListener(), this);

        // Start periodic tasks.
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this,
                new TimeModifiersUpdater(), 0L, 20L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this,
                new ManaUpdater(), 0L, 1L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this,
                this.scoreboardsList, 0L, 5L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this.cooldowns,
                20 * 60L, 20 * 60L);

        // Load quests and QuestManager.
        this.questManager = new QuestManager();
        this.questManager.loadAll();

        if (this.getConfig().getBoolean("debug", false)) {
            Debug.onEnable();
        }
    }

    @Override
    public void onDisable() {
        // Kick all players to avoid data loss.
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.kickPlayer("Server is reloading, please reconnect.");
        }
        // Clear map and disable componenets.
        this.loadedProfiles.clear();
        this.questManager.shutdown();
        Party.clearParties();
        // Save config.
        this.saveConfig();
    }

    public Profile getProfile(final UUID uuid) {
        return this.loadedProfiles.get(uuid);
    }

    public Profile getProfile(final OfflinePlayer player) {
        return getProfile(player.getUniqueId());
    }

    public InventoryMenu getCharacterChoser() {
        return this.characterChooserMenu;
    }

    public File getFile(final String... more) {
        return this.getDataFolderPath(more).toFile();
    }

    public QuestManager getQuestManager() {
        return this.questManager;
    }

    public Cooldowns getCooldowns() {
        return cooldowns;
    }

    @EventHandler
    private void onJoin(final PlayerJoinEvent event) {
        this.loadOrCreateProfile(event.getPlayer().getUniqueId());

        if (!this.getProfile(event.getPlayer()).hasCharacter()) {
            // Hasn't character

            // Clear display name.
            event.getPlayer().setDisplayName(event.getPlayer().getName());
            // Show character chooser.
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                @Override
                public void run() {
                    RpgPlugin.this.characterChooserMenu.showTo(event
                            .getPlayer());
                }
            }, 5L);
        } else {
            // Has character.
            Character character = this.getProfile(event.getPlayer())
                    .getCharacter();
            // Apply walk speed modifier.
            event.getPlayer().setWalkSpeed(
                    0.2F * character.getModifiers().getWalkSpeedModifier());

            StringBuilder quests = new StringBuilder();
            for (String questId : this.getProfile(event.getPlayer())
                    .getActiveQuestIds()) {
                quests.append(questId).append(", ");
            }

            event.getPlayer().sendMessage(
                    "Welcome back! Current quest(s): " + ChatColor.LIGHT_PURPLE
                            + quests.toString());

            // Apply scoreboard.
            PlayerStatsScoreboard scoreboard = new PlayerStatsScoreboard(
                    event.getPlayer());
            this.scoreboardsList.add(scoreboard);
        }
    }

    @EventHandler
    private void onLeave(final PlayerQuitEvent event) {
        // Remove him from party if needed.
        if (Party.getParty(event.getPlayer()) != null) {
            Party.getParty(event.getPlayer()).removePlayer(event.getPlayer());
        }

        // Remove his scoreboard.
        if (event.getPlayer().getScoreboard() != null) {
            this.scoreboardsList.remove(event.getPlayer());
        }

        this.saveProfile(event.getPlayer().getUniqueId());
    }

    @EventHandler
    private void onRespawn(final PlayerRespawnEvent event) {
        // Remove character.
        this.getProfile(event.getPlayer()).setCharacter(null);
        // Remove scoreboard.
        this.scoreboardsList.remove(event.getPlayer());
        // Show character selection after respawn.
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                RpgPlugin.this.characterChooserMenu.showTo(event.getPlayer());
            }
        }, 20L);

    }

    @EventHandler
    private void onInventoryClick(final InventoryClickEvent event) {
        InventoryMenu menu = InventoryMenu.getInventoryMenu(event
                .getInventory());
        if (menu != null) {
            if (event.getWhoClicked() instanceof Player) {
                menu.inventoryClick((Player) event.getWhoClicked(),
                        event.getSlot());
                event.setCancelled(true);
            }
        }
    }

    private Path getDataFolderPath(final String... more) {
        return Paths.get(this.dataFolder, more);
    }

    private void loadOrCreateProfile(final UUID uniqueId) {
        if (this.profileExists(uniqueId)) {
            this.log.info("Loading profile for " + uniqueId.toString());
            try {
                Profile profile = new Profile();
                YamlConfiguration conf = YamlConfiguration
                        .loadConfiguration(this.getDataFolderPath("profiles",
                                uniqueId.toString() + ".yml").toFile());
                profile.setUniqueId(uniqueId);
                profile.setCharacter(Characters.fromId(conf
                        .getString("character")));
                profile.setXp(conf.getLong("xp"));
                profile.setFlorins(conf.getInt("florins"));
                profile.setDollars(conf.getInt("dollars"));
                profile.setActiveQuestIds(getStringList(conf.getList("activequests", new ArrayList<String>())));

                Map<String, Object> values = conf.getConfigurationSection(
                        "completedQuests").getValues(false);
                Map<String, Boolean> completedQuests = new HashMap<String, Boolean>();
                for (Entry<String, Object> value : values.entrySet()) {
                    completedQuests.put(value.getKey(),
                            Boolean.valueOf(value.toString()));
                }
                profile.setCompletedQuests(completedQuests);

                if (profile.getCharacter() == Characters.MAGICAN) {
                    profile.setMagican_currentSpell(conf
                            .getInt("magican.currentSpell"));
                    profile.setMana(conf.getInt("magican.mana", 0));
                    profile.setMaxMana(conf.getInt("magican.maxmana", 900));
                }

                this.loadedProfiles.put(uniqueId, profile);

                if (profile != null) {
                    this.loadedProfiles.put(profile.getUniqueId(), profile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.log.info("Creating new profile for " + uniqueId.toString());
            this.loadedProfiles.put(uniqueId, new Profile(uniqueId, null));
        }
    }

    private void saveProfile(final UUID uniqueId) {
        this.log.info("Saving profile for " + uniqueId.toString());
        try {
            Profile profile = this.loadedProfiles.get(uniqueId);
            YamlConfiguration conf = new YamlConfiguration();
            conf.set("uuid", profile.getUniqueId().toString());

            if (profile.hasCharacter()) {
                conf.set("character", profile.getCharacter().getId());
            }

            if (profile.getCharacter() == Characters.MAGICAN) {
                conf.set("magican.currentSpell",
                        profile.getMagican_currentSpell());
                conf.set("magican.mana", profile.getMana());
                conf.set("magican.maxmana", profile.getMaxMana());
            }

            conf.set("activequests", profile.getActiveQuestIds());
            conf.set("xp", profile.getXp());
            conf.set("completedQuests", profile.getCompletedQuests());
            conf.set("florins", profile.getFlorins());
            conf.set("dollars", profile.getDollars());
            conf.save(this.getDataFolderPath("profiles",
                    uniqueId.toString() + ".yml").toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean profileExists(final UUID uniqueId) {
        return this.getDataFolderPath("profiles", uniqueId.toString() + ".yml")
                .toFile().exists();
    }

    private void expandDirectoryStructure() {
        boolean expanded = false;
        expanded |= new File(this.dataFolder + "/profiles/").mkdirs();
        expanded |= new File(this.dataFolder + "/quests/").mkdirs();
        if (expanded) {
            this.log.info("Directory structure expanded!");
        }
    }

    private void createCharacterChooser() {
        class SelectCharacterAction implements Action {
            private Character character;

            public SelectCharacterAction(Character character) {
                this.character = character;
            }

            public void execute(Player player) {
                RpgPlugin.getInstance().getProfile(player)
                        .setCharacter(this.character);
                this.character.applyTo(player);
                // Apply scoreboard.
                PlayerStatsScoreboard scoreboard = new PlayerStatsScoreboard(
                        player);
                RpgPlugin.this.scoreboardsList.add(scoreboard);
                player.sendMessage(ChatColor.GREEN + "Your class is now: "
                        + ChatColor.GOLD + this.character.getName());
            }
        }

        List<InventoryMenuItem> items = new ArrayList<InventoryMenuItem>();

        // Standart characters.
        items.add(new InventoryMenuItem(Characters.ADVENTURER
                .getIcon(Material.IRON_CHESTPLATE), new SelectCharacterAction(
                Characters.ADVENTURER), 0, true));
        items.add(new InventoryMenuItem(
                Characters.HUNTER.getIcon(Material.BOW),
                new SelectCharacterAction(Characters.HUNTER), 1, true));
        items.add(new InventoryMenuItem(Characters.KILLER
                .getIcon(Material.SKULL_ITEM), new SelectCharacterAction(
                Characters.KILLER), 2, true));
        items.add(new InventoryMenuItem(Characters.KNIGHT
                .getIcon(Material.DIAMOND_SWORD), new SelectCharacterAction(
                Characters.KNIGHT), 3, true));
        items.add(new InventoryMenuItem(Characters.MAGICAN
                .getIcon(Material.BOOK), new SelectCharacterAction(
                Characters.MAGICAN), 4, true));
        items.add(new InventoryMenuItem(Characters.SOLDIER
                .getIcon(Material.IRON_AXE), new SelectCharacterAction(
                Characters.SOLDIER), 5, true));

        // VIP Characters.
        items.add(new InventoryMenuItem(Characters.UNDEAD.getIcon(
                Material.SKULL_ITEM, (byte) 2, (short) 2),
                new SelectCharacterAction(Characters.UNDEAD), 6, true));
        items.add(new InventoryMenuItem(Characters.VAMPIRE
                .getIcon(new WorkingPotion(PotionType.INSTANT_HEAL)
                        .toItemStack(1)), new SelectCharacterAction(
                Characters.VAMPIRE), 7, true));
        items.add(new InventoryMenuItem(Characters.WEREWOLF
                .getIcon(Material.BONE), new SelectCharacterAction(
                Characters.WEREWOLF), 8, true));

        this.characterChooserMenu = new InventoryMenu(9,
                ChatColor.BOLD.toString() + ChatColor.WHITE
                        + "Choose a character:", items);
    }
}
