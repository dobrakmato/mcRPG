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
	public static final Character KNIGHT  = new Knight();
	public static final Character HUNTER  = new Hunter();
	public static final Character KILLER  = new Killer();
	public static final Character MAGICAN = new Magican();
	public static final Character ADVENTURER = new Adventurer();
	
	// VIP
	public static final Character DARK_MAGICAN = null;
	public static final Character VAMPIRE = new Vampire();
	public static final Character UNDEAD = new Undead();
	public static final Character WEREWOLF = new Werewolf();
}
