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

public class SquareAffectedBlocksDef extends PaintedAffectedBlocksDef implements
		AffectedBlocksDefinition {

	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	private int minZ;
	private int maxZ;

	public SquareAffectedBlocksDef(int radius, Location center) {
		super(radius, center);

		this.minX = center.getBlockX() - radius;
		this.maxX = center.getBlockX() + radius;
		if (this.isFullHeight()) {
			this.minY = center.getBlockY() - radius;
			this.maxY = center.getBlockY() + radius;
		}
		this.minZ = center.getBlockZ() - radius;
		this.maxZ = center.getBlockZ() + radius;
	}

	@Override
	public Block[] getAffectedBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAffected(Block block) {
		// TODO: isAffected(Block);
		return false;
	}

	@Override
	public boolean isFullHeight() {
		// TODO Auto-generated method stub
		return false;
	}

}
