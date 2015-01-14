package eu.matejkormuth.rpgdavid.characters;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.MagicBook;
import eu.matejkormuth.rpgdavid.Modifiers;
import eu.matejkormuth.rpgdavid.inventoryutils.Armor;

public class Magican extends Character {
	public Magican() {
		super("Kouzelník", "Magic Stick with spells.", new Modifiers(1, 1, 1, 1, 1), Armor.EMPTY, new MagicBook());
	}
}
