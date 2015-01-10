package eu.matejkormuth.rpgdavid.characters;

import org.bukkit.Color;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Modifiers;
import eu.matejkormuth.rpgdavid.inventoryutils.Armor;

public class Killer extends Character {
	public Killer() {
		super("Vrah", new Modifiers(1, 1, 1, 1.5F, 1), Armor.createLether(Color.BLACK), new Potion(PotionType.INSTANT_DAMAGE, 1).toItemStack(4));
	}
}
