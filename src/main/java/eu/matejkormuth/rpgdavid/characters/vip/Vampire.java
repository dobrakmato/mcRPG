package eu.matejkormuth.rpgdavid.characters.vip;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Modifiers;
import eu.matejkormuth.rpgdavid.inventoryutils.Armor;

public class Vampire extends Character {
	public Vampire() {
		super("Upír", new Modifiers(1, 1, 1, 1, 1), new Armor(new ItemStack(Material.GOLD_HELMET), null, null, null));
	}
}
