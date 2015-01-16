/*
 *  mcRPG is a open source rpg bukkit/spigot plugin.
 *  Copyright (C) 2015 Matej Kormuth 
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
		if(id == null) {
			return null;
		}
		
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
