package eu.matejkormuth.rpgdavid;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import eu.matejkormuth.rpgdavid.commands.CharacterCommandExecutor;
import eu.matejkormuth.rpgdavid.commands.PartyCommandExecutor;
import eu.matejkormuth.rpgdavid.commands.PlayerHeadCommandExecutor;
import eu.matejkormuth.rpgdavid.inventorymenu.Action;
import eu.matejkormuth.rpgdavid.inventorymenu.InventoryMenu;
import eu.matejkormuth.rpgdavid.inventorymenu.InventoryMenuItem;
import eu.matejkormuth.rpgdavid.listeners.AdventurerListener;
import eu.matejkormuth.rpgdavid.listeners.HunterListener;
import eu.matejkormuth.rpgdavid.listeners.KnightListener;
import eu.matejkormuth.rpgdavid.listeners.ModifiersListener;
import eu.matejkormuth.rpgdavid.listeners.UndeadListener;
import eu.matejkormuth.rpgdavid.listeners.VampireListener;
import eu.matejkormuth.rpgdavid.party.Party;

public class RpgPlugin extends JavaPlugin implements Listener {
	private static RpgPlugin instnace;

	public static RpgPlugin getInstance() {
		return instnace;
	}

	private Logger log;
	private String dataFolder;
	private Map<UUID, Profile> loadedProfiles;

	private InventoryMenu characterChooserMenu;

	@Override
	public void onEnable() {
		instnace = this;

		this.loadedProfiles = new HashMap<UUID, Profile>();

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

		// Register event handlers.
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new ModifiersListener(), this);
		Bukkit.getPluginManager()
				.registerEvents(new AdventurerListener(), this);
		Bukkit.getPluginManager().registerEvents(new HunterListener(), this);
		Bukkit.getPluginManager().registerEvents(new UndeadListener(), this);
		Bukkit.getPluginManager().registerEvents(new KnightListener(), this);
		Bukkit.getPluginManager().registerEvents(new VampireListener(), this);

		// Start periodic tasks.
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this,
				new TimeModifiersUpdater(), 0L, 20L);
	}

	@Override
	public void onDisable() {

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

	@EventHandler
	private void onJoin(final PlayerJoinEvent event) {
		this.loadOrCreateProfile(event.getPlayer().getUniqueId());

		if (!this.getProfile(event.getPlayer()).hasCharacter()) {
			// Hasn't character
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

			event.getPlayer().sendMessage(
					"Welcome back! You character is: " + ChatColor.GOLD
							+ character.getName() + ChatColor.WHITE
							+ ". Your xp: " + ChatColor.RED + 
							+ this.getProfile(event.getPlayer()).getXp());
		}
	}

	@EventHandler
	private void onLeave(final PlayerQuitEvent event) {
		// Remove him from party if needed.
		if (Party.getParty(event.getPlayer()) != null) {
			Party.getParty(event.getPlayer()).removePlayer(event.getPlayer());
		}

		this.saveProfile(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onRespawn(final PlayerRespawnEvent event) {
		// Remove character.
		this.getProfile(event.getPlayer()).setCharacter(null);
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
			conf.set("xp", profile.getXp());
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
				player.sendMessage(ChatColor.GREEN + "Your class is now: "
						+ ChatColor.GOLD + this.character.getName());
			}
		}

		List<InventoryMenuItem> items = new ArrayList<InventoryMenuItem>();

		// Standart characters.
		items.add(new InventoryMenuItem(Characters.ADVENTURER
				.getIcon(Material.IRON_CHESTPLATE), new SelectCharacterAction(
				Characters.ADVENTURER), 0, true));
		items.add(new InventoryMenuItem(Characters.HUNTER
				.getIcon(Material.IRON_CHESTPLATE), new SelectCharacterAction(
				Characters.HUNTER), 1, true));
		items.add(new InventoryMenuItem(Characters.KILLER
				.getIcon(Material.IRON_CHESTPLATE), new SelectCharacterAction(
				Characters.KILLER), 2, true));
		items.add(new InventoryMenuItem(Characters.KNIGHT
				.getIcon(Material.IRON_CHESTPLATE), new SelectCharacterAction(
				Characters.KNIGHT), 3, true));
		items.add(new InventoryMenuItem(Characters.MAGICAN
				.getIcon(Material.IRON_CHESTPLATE), new SelectCharacterAction(
				Characters.MAGICAN), 4, true));
		items.add(new InventoryMenuItem(Characters.SOLDIER
				.getIcon(Material.IRON_CHESTPLATE), new SelectCharacterAction(
				Characters.SOLDIER), 5, true));

		// VIP Characters.
		items.add(new InventoryMenuItem(Characters.UNDEAD
				.getIcon(Material.GOLD_CHESTPLATE), new SelectCharacterAction(
				Characters.UNDEAD), 6, true));
		items.add(new InventoryMenuItem(Characters.VAMPIRE
				.getIcon(Material.GOLD_CHESTPLATE), new SelectCharacterAction(
				Characters.VAMPIRE), 7, true));
		items.add(new InventoryMenuItem(Characters.WEREWOLF
				.getIcon(Material.GOLD_CHESTPLATE), new SelectCharacterAction(
				Characters.WEREWOLF), 8, true));

		this.characterChooserMenu = new InventoryMenu(9,
				ChatColor.BOLD.toString() + ChatColor.WHITE
						+ "Choose a character:", items);
	}
}
