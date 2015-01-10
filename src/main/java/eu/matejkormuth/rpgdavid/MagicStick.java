package eu.matejkormuth.rpgdavid;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MagicStick extends ItemStack {
	public MagicStick() {
		super(Material.STICK, 1);
		ItemMeta im = this.getItemMeta();
		im.setDisplayName(ChatColor.RESET.toString() + ChatColor.YELLOW + "Magic Stick");
		im.setLore(Arrays.asList(ChatColor.RESET.toString() + "Level 1"));
		this.setItemMeta(im);
	}
	
	public int getLevel() {
		String str = this.getItemMeta().getLore().get(0);
		return Integer.valueOf(str.substring(str.indexOf("Level") + 6));
	}
	
	public void setLevel(int level) {
		// After level 5 stick is blaze rod; before level 5 stick is wooden stick.
		if(level >= 5) {
			this.setType(Material.BLAZE_ROD);
		} else {
			this.setType(Material.STICK);
		}
		
		// Update meta.
		ItemMeta im = this.getItemMeta();
		im.setLore(Arrays.asList(ChatColor.RESET.toString() + "Level " + level));
		this.setItemMeta(im);
	}
}
