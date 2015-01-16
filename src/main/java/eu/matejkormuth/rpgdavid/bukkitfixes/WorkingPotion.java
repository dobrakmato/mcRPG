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
package eu.matejkormuth.rpgdavid.bukkitfixes;

import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class WorkingPotion extends Potion {
	public WorkingPotion(PotionType type) {
		super(type);
	}
	
	public WorkingPotion(PotionType type, int level) {
		super(type, level);
	}

	public Potion splash() {
		this.setSplash(true);
		return this;
	}
}
