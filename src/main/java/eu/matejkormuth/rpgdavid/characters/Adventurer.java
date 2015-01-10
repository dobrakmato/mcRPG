package eu.matejkormuth.rpgdavid.characters;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Modifiers;
import eu.matejkormuth.rpgdavid.inventoryutils.Armor;

public class Adventurer extends Character {
	public Adventurer() {
		super("Dobrodruh", new Modifiers(1, 1, 1, 1, 1), Armor.EMPTY);
	}
}
