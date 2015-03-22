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
package eu.matejkormuth.rpgdavid.starving.items.firearms;

import eu.matejkormuth.rpgdavid.starving.items.AmunitionType;
import eu.matejkormuth.rpgdavid.starving.items.Mapping;
import eu.matejkormuth.rpgdavid.starving.items.Mappings;
import eu.matejkormuth.rpgdavid.starving.items.base.Firearm;

public class M16 extends Firearm {

	public M16() {
		this(Mappings.M16, "M16");
	}

	public M16(Mapping mapping, String name) {
		super(mapping, name);
		this.setAmmoType(AmunitionType.LONG);
		this.setClipSize(45);
		this.setFireRate(14);
		this.setInaccurancy(0.4f);
		this.setScopedInaccurancy(0.04f);
		this.setNoiseLevel(0.7f);
		this.setProjectileSpeed(3.5f);
		this.setRecoil(0.55f);
		this.setReloadTime(40);
	}
}
