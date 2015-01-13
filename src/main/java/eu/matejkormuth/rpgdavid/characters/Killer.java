package eu.matejkormuth.rpgdavid.characters;

import org.bukkit.Color;
import org.bukkit.potion.PotionType;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Modifiers;
import eu.matejkormuth.rpgdavid.bukkitfixes.WorkingPotion;
import eu.matejkormuth.rpgdavid.inventoryutils.Armor;

public class Killer extends Character {
	public Killer() {
		super("Vrah", null, new Modifiers(1, 1, 1, 1.5F, 1), Armor
				.createLether(Color.BLACK), new WorkingPotion(
				PotionType.INSTANT_DAMAGE, 1).splash().toItemStack(4));
	}
}
