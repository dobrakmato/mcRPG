/*
 *  Starving is a open source bukkit/spigot mmo game.
 *  Copyright (C) 2014-2015 Matej Kormuth
 *  This file is a part of Starving. <http://www.starving.eu>
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
package eu.matejkormuth.rpgdavid.starving.items.base;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.items.Category;
import eu.matejkormuth.rpgdavid.starving.items.Mapping;

public class MeleeWeapon extends Item {

	private double baseDmg;
	private int durability;

	public MeleeWeapon(Mapping mapping, String name, double baseDmg,
			int durability) {
		super(mapping, name);
		this.baseDmg = baseDmg;
		this.durability = durability;
		this.setCategory(Category.MELEE);
		this.setMaxStackAmount(1);
	}

	public void onAttack(Player damager, LivingEntity entity, double damage) {
		entity.damage(baseDmg);
	}

	public int getDurability() {
		return durability;
	}

	public double getBaseDmg() {
		return baseDmg;
	}
}
