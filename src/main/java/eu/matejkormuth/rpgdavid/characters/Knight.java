package eu.matejkormuth.rpgdavid.characters;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Modifiers;
import eu.matejkormuth.rpgdavid.inventoryutils.Armor;

public class Knight extends Character {
	public Knight() {
		super("Rytíø", new Modifiers(1.1F, 1, 1, 1, 1), new Armor(new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), null, null));
	}
}
