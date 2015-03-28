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
package eu.matejkormuth.rpgdavid.starving.worldgen;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class CircleAffectedBlocksDef extends PaintedAffectedBlocksDef implements
		AffectedBlocksDefinition {

	public CircleAffectedBlocksDef(int radius, Location center) {
		super(radius, center);
	}

	@Override
	public Block[] getAffectedBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAffected(Block block) {
		return block.getLocation().distanceSquared(this.center) < this.radiusPow2;
	}

	@Override
	public boolean isFullHeight() {
		// TODO Auto-generated method stub
		return false;
	}

}
