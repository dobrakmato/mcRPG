package eu.matejkormuth.rpgdavid;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Strings;

import eu.matejkormuth.rpgdavid.inventoryutils.Armor;
import eu.matejkormuth.rpgdavid.inventoryutils.ItemStackBuilder;

public class Character {
	private final String name;
	private final String id;
	private final String special;

	private final Modifiers modifiers;
	private final Armor armor;
	private final ItemStack[] items;

	public Character(final String name, final String special,
			final Modifiers modifiers, final Armor armor,
			final ItemStack... items) {
		this.name = name;
		this.modifiers = modifiers;
		this.items = items;
		this.armor = armor;

		this.special = special;

		this.id = this.getClass().getSimpleName();
	}

	public Modifiers getModifiers() {
		return modifiers;
	}

	public String getName() {
		return name;
	}

	public ItemStack[] getItems() {
		return items;
	}

	public Armor getArmor() {
		return armor;
	}

	public String getId() {
		return id;
	}

	public void applyTo(Player p) {
		// Clear player.
		p.getInventory().clear();

		// Apply armor.
		p.getInventory().setHelmet(this.armor.getHelmet());
		p.getInventory().setChestplate(this.armor.getChestplate());
		p.getInventory().setLeggings(this.armor.getLeggings());
		p.getInventory().setBoots(this.armor.getBoots());

		// Apply modifiers.
		p.setWalkSpeed(0.2F * this.modifiers.getWalkSpeedModifier());
		p.setMaxHealth(20D * this.modifiers.getHealthModifier());
		p.setHealth(p.getMaxHealth());

		// Give items.
		for (ItemStack item : this.items) {
			p.getInventory().addItem(item);
		}
	}

	public ItemStack getIcon(Material material) {
		List<String> lore = new ArrayList<String>(8);

		lore.add(ChatColor.GREEN + "Health: " + ChatColor.GOLD
				+ +(int) (20D * this.modifiers.getHealthModifier()) + " HP");
		lore.add(ChatColor.YELLOW + "Health regain: " + ChatColor.GOLD
				+ +(int) (100D * this.modifiers.getHealthRegainModifier())
				+ "%");
		lore.add(ChatColor.RED + "Criticals chance: " + ChatColor.GOLD
				+ +(int) (100D * this.modifiers.getCriticalModifier()) + "%");
		lore.add(ChatColor.RED + "Damage: " + ChatColor.GOLD
				+ +(int) (100D * this.modifiers.getDamageModifier()) + "%");
		lore.add(ChatColor.BLUE + "Walk speed: " + ChatColor.GOLD
				+ +(int) (100D * this.modifiers.getWalkSpeedModifier()) + "%");

		lore.add("");
		if(Strings.isNullOrEmpty(this.special)) {
			lore.add(ChatColor.LIGHT_PURPLE + "Special: " + ChatColor.WHITE + "nothing");
		} else {
			lore.add(ChatColor.LIGHT_PURPLE + "Special: " + ChatColor.WHITE + this.special);
		}
		lore.add("");

		return new ItemStackBuilder(material)
				.name(ChatColor.RESET + this.getName()).lore(lore).build();
	}
}
