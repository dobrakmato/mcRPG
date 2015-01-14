package eu.matejkormuth.rpgdavid;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MagicBook extends ItemStack {
	public MagicBook() {
		super(Material.BOOK, 1);
		ItemMeta im = this.getItemMeta();
		im.setDisplayName(ChatColor.RESET.toString() + ChatColor.YELLOW + "Magic Book");
		im.setLore(Arrays.asList(ChatColor.RESET.toString() + "Level 1"));
		this.setItemMeta(im);
	}
	
	public int getLevel() {
		String str = this.getItemMeta().getLore().get(0);
		return Integer.valueOf(str.substring(str.indexOf("Level") + 6));
	}
	
	public void setLevel(int level) {
		// Now it is book on every level. OLD: After level 5 stick is blaze rod; before level 5 stick is wooden stick.
		if(level >= 5) {
			this.setType(Material.BOOK);
		} else {
			this.setType(Material.BOOK);
		}
		
		// Update meta.
		ItemMeta im = this.getItemMeta();
		im.setLore(Arrays.asList(ChatColor.RESET.toString() + "Level " + level));
		this.setItemMeta(im);
	}
}
