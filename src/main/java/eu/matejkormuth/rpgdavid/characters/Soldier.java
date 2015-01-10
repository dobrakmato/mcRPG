package eu.matejkormuth.rpgdavid.characters;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Modifiers;
import eu.matejkormuth.rpgdavid.inventoryutils.Armor;

public final class Soldier extends Character {
	public Soldier() {
		super("V�le�n�k", new Modifiers(1, 1, 1.2F, 1, 1), Armor.EMPTY);
	}
}
