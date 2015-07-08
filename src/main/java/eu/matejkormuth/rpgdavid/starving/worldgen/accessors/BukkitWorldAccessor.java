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
package eu.matejkormuth.rpgdavid.starving.worldgen.accessors;

import org.bukkit.Material;
import org.bukkit.World;

import eu.matejkormuth.rpgdavid.starving.worldgen.WorldAccessor;

public class BukkitWorldAccessor extends WorldAccessor {

    private World w;

    public BukkitWorldAccessor(World world) {
        super(world);
        this.w = world;
    }

    @Override
    public Material getMaterialAt(int x, int y, int z) {
        return w.getBlockAt(x, y, z).getType();
    }

    @SuppressWarnings("deprecation")
    @Override
    public byte getDataAt(int x, int y, int z) {
        return w.getBlockAt(x, y, z).getData();
    }

    @Override
    public void setMaterialAt(int x, int y, int z, Material material) {
        w.getBlockAt(x, y, z).setType(material);
    }

    @Override
    public void setMaterialAt(int x, int y, int z, Material material,
            boolean applyPhysics) {
        w.getBlockAt(x, y, z).setType(material, applyPhysics);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setDataAt(int x, int y, int z, byte data) {
        w.getBlockAt(x, y, z).setData(data);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setDataAt(int x, int y, int z, byte data, boolean applyPhysics) {
        w.getBlockAt(x, y, z).setData(data, applyPhysics);
    }

    @Override
    public int getHighestBlockAt(int x, int z) {
        return w.getHighestBlockYAt(x, z);
    }

}
