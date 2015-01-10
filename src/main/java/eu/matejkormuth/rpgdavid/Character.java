package eu.matejkormuth.rpgdavid;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.inventoryutils.Armor;

public class Character {
	private final String name;
	private final String id;
	
	private final Modifiers modifiers;
	private final Armor armor;
	private final ItemStack[] items;
	
	public Character(String name, Modifiers modifiers, Armor armor, ItemStack... items) {
		this.name = name;
		this.modifiers = modifiers;
		this.items = items;
		this.armor = armor;
		
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
		p.setWalkSpeed(0.1F * this.modifiers.getWalkSpeedModifier());
		p.setMaxHealth(20D * this.modifiers.getHealthModifier());
		p.setHealth(p.getMaxHealth());
		
		// Give items.
		for(ItemStack item : this.items) {
			p.getInventory().addItem(item);
		}
	}
}
