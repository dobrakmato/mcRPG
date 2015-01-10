package eu.matejkormuth.rpgdavid;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
import org.bukkit.plugin.java.JavaPlugin;

import eu.matejkormuth.rpgdavid.commands.PartyCommandExecutor;
import eu.matejkormuth.rpgdavid.commands.PlayerHeadCommandExecutor;
import eu.matejkormuth.rpgdavid.inventorymenu.Action;
import eu.matejkormuth.rpgdavid.inventorymenu.InventoryMenu;
import eu.matejkormuth.rpgdavid.inventorymenu.InventoryMenuItem;
import eu.matejkormuth.rpgdavid.inventoryutils.ItemStackBuilder;
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
			}, 20L);
		} else {
			// Has character.
			Character character = this.getProfile(event.getPlayer())
					.getCharacter();
			// Apply walk speed modifier.
			event.getPlayer().setWalkSpeed(
					0.1F * character.getModifiers().getWalkSpeedModifier());
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
	private void onInventoryClick(final InventoryClickEvent event) {
		InventoryMenu menu = InventoryMenu.getInventoryMenu(event
				.getClickedInventory());
		if (menu != null) {
			if(event.getWhoClicked() instanceof Player) {
				menu.inventoryClick((Player) event.getWhoClicked(), event.getSlot());
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
			conf.set("character", profile.getCharacter().getId());
			conf.set("xp", profile.getXp());
			conf.save(this.getDataFolderPath("profiles",
					uniqueId.toString() + ".yml").toFile());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private boolean profileExists(final UUID uniqueId) {
		return Files.exists(this.getDataFolderPath("profiles",
				uniqueId.toString() + ".yml"));
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
		items.add(new InventoryMenuItem(new ItemStackBuilder(
				Material.IRON_CHESTPLATE).name("Adventurer").build(),
				new SelectCharacterAction(Characters.ADVENTURER), 0, true));
		items.add(new InventoryMenuItem(new ItemStackBuilder(
				Material.IRON_CHESTPLATE).name("Hunter").build(),
				new SelectCharacterAction(Characters.ADVENTURER), 1, true));
		items.add(new InventoryMenuItem(new ItemStackBuilder(
				Material.IRON_CHESTPLATE).name("Killer").build(),
				new SelectCharacterAction(Characters.ADVENTURER), 2, true));
		items.add(new InventoryMenuItem(new ItemStackBuilder(
				Material.IRON_CHESTPLATE).name("Knight").build(),
				new SelectCharacterAction(Characters.ADVENTURER), 3, true));
		items.add(new InventoryMenuItem(new ItemStackBuilder(
				Material.IRON_CHESTPLATE).name("Magican").build(),
				new SelectCharacterAction(Characters.ADVENTURER), 4, true));
		items.add(new InventoryMenuItem(new ItemStackBuilder(
				Material.IRON_CHESTPLATE).name("Soldier").build(),
				new SelectCharacterAction(Characters.ADVENTURER), 5, true));

		items.add(new InventoryMenuItem(new ItemStackBuilder(
				Material.GOLD_CHESTPLATE).name("[VIP] Undead").build(),
				new SelectCharacterAction(Characters.ADVENTURER), 6, true));
		items.add(new InventoryMenuItem(new ItemStackBuilder(
				Material.GOLD_CHESTPLATE).name("[VIP] Vampire").build(),
				new SelectCharacterAction(Characters.ADVENTURER), 7, true));
		items.add(new InventoryMenuItem(new ItemStackBuilder(
				Material.GOLD_CHESTPLATE).name("[VIP] Werewofl").build(),
				new SelectCharacterAction(Characters.ADVENTURER), 8, true));

		this.characterChooserMenu = new InventoryMenu(9, ChatColor.GREEN
				+ "Choose a character:", items);
	}
}
