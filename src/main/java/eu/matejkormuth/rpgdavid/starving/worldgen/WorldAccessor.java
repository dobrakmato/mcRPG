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

import org.bukkit.Material;
import org.bukkit.World;

public abstract class WorldAccessor {

    public WorldAccessor(World world) {
    }

    public abstract Material getMaterialAt(int x, int y, int z);

    public abstract byte getDataAt(int x, int y, int z);

    public abstract void setMaterialAt(int x, int y, int z, Material material);

    public abstract void setMaterialAt(int x, int y, int z, Material material,
            boolean applyPhysics);

    public abstract void setDataAt(int x, int y, int z, byte data);

    public abstract void setDataAt(int x, int y, int z, byte data,
            boolean applyPhysics);

    public abstract int getHighestBlockAt(int x, int z);
}
