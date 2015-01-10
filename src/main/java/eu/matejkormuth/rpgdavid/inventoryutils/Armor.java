package eu.matejkormuth.rpgdavid.inventoryutils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public final class Armor {
	public static final Armor EMPTY = new Armor(null, null, null, null);
	
	private final ItemStack helmet;
	private final ItemStack chestplate;
	private final ItemStack leggings;
	private final ItemStack boots;
	
	public Armor(ItemStack helmet, ItemStack chestplate, ItemStack leggings,
			ItemStack boots) {
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
	}

	public ItemStack getHelmet() {
		return helmet;
	}

	public ItemStack getChestplate() {
		return chestplate;
	}

	public ItemStack getLeggings() {
		return leggings;
	}

	public ItemStack getBoots() {
		return boots;
	}
	
	public static Armor createLether(Color color) {
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		ItemMeta im = helmet.getItemMeta();
		((LeatherArmorMeta)im).setColor(color);
		helmet.setItemMeta(im);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		chestplate.setItemMeta(im);
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		leggings.setItemMeta(im);
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		boots.setItemMeta(im);
		
		return new Armor(helmet, chestplate, leggings, boots);
	}
}
