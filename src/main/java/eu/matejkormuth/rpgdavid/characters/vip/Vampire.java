package eu.matejkormuth.rpgdavid.characters.vip;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Modifiers;
import eu.matejkormuth.rpgdavid.inventoryutils.Armor;

public class Vampire extends Character {
	public Vampire() {
		super("Upír", "Can bite each one minute.\n230% walking speed in nigth.\nNight vision in night.\nSunlight damages them if not wearing gold helmet.", Modifiers.DEFAULT, new Armor(new ItemStack(Material.GOLD_HELMET), null, null, null));
	}
}
