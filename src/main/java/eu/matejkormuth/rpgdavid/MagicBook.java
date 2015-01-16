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
		
		// TODO: Make spells.
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
