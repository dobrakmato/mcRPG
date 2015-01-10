package eu.matejkormuth.rpgdavid;

import eu.matejkormuth.rpgdavid.characters.Adventurer;
import eu.matejkormuth.rpgdavid.characters.Hunter;
import eu.matejkormuth.rpgdavid.characters.Killer;
import eu.matejkormuth.rpgdavid.characters.Knight;
import eu.matejkormuth.rpgdavid.characters.Magican;
import eu.matejkormuth.rpgdavid.characters.Soldier;
import eu.matejkormuth.rpgdavid.characters.vip.Undead;
import eu.matejkormuth.rpgdavid.characters.vip.Vampire;
import eu.matejkormuth.rpgdavid.characters.vip.Werewolf;

public final class Characters {
	// Standard
	public static final Character SOLDIER = new Soldier();
	public static final Character KNIGHT = new Knight();
	public static final Character HUNTER = new Hunter();
	public static final Character KILLER = new Killer();
	public static final Character MAGICAN = new Magican();
	public static final Character ADVENTURER = new Adventurer();

	// VIP
	public static final Character VAMPIRE = new Vampire();
	public static final Character UNDEAD = new Undead();
	public static final Character WEREWOLF = new Werewolf();

	public static Character fromId(String id) {
		switch (id) {
			case "Soldier":
				return Characters.SOLDIER;
			case "Knight":
				return Characters.KNIGHT;
			case "Hunter":
				return Characters.HUNTER;
			case "Killer":
				return Characters.KILLER;
			case "Magican":
				return Characters.MAGICAN;
			case "Adventurer":
				return Characters.ADVENTURER;
				
			case "Vampire":
				return Characters.ADVENTURER;
			case "Undead":
				return Characters.ADVENTURER;
			case "Werewolf":
				return Characters.ADVENTURER;
				
			default:
					return null;
		}
	}
}
