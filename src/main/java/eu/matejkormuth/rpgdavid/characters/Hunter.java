package eu.matejkormuth.rpgdavid.characters;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Modifiers;
import eu.matejkormuth.rpgdavid.inventoryutils.Armor;

public class Hunter extends Character {
	public Hunter() {
		super("Lovec", "Arrow has +1,5 HP DMG.", new Modifiers(1, 1.1F, 1, 1, 1), Armor.EMPTY, new ItemStack(Material.BOW), new ItemStack(Material.ARROW, 5));
	}
}
