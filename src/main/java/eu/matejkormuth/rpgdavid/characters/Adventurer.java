package eu.matejkormuth.rpgdavid.characters;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Modifiers;
import eu.matejkormuth.rpgdavid.inventoryutils.Armor;

public class Adventurer extends Character {
	public Adventurer() {
		super("Dobrodruh", "Hunger depletes 2 times slower. Potions have 2 times bigger radius", new Modifiers(1, 1, 1, 1, 1), Armor.EMPTY);
	}
}
