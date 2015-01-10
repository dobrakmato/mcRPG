package eu.matejkormuth.rpgdavid.characters.vip;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Modifiers;
import eu.matejkormuth.rpgdavid.inventoryutils.Armor;

public class Werewolf extends Character {
	public Werewolf() {
		super("Vlkodlak", new Modifiers(1, 1, 1, 1, 1), Armor.EMPTY); // Modifiers handler differently
	}
}
