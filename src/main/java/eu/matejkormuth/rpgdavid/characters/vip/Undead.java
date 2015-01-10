package eu.matejkormuth.rpgdavid.characters.vip;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Modifiers;
import eu.matejkormuth.rpgdavid.inventoryutils.Armor;

public class Undead extends Character {
	public Undead() {
		super("Nemrtvý", "Regeneration for 3 seconds after consuming rotten flesh.", new Modifiers(1, 1, 1, 1, 1), Armor.EMPTY);
	}
}
