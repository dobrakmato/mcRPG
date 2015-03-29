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
package eu.matejkormuth.rpgdavid.starving;

import org.bukkit.block.Biome;

public enum EnviromentType {
    WARM, NORMAL, COLD;

    public static final EnviromentType byBiome(Biome biome) {
        // Table by issue 33.
        switch (biome) {
            case OCEAN:
                return COLD;
            case PLAINS:
                return NORMAL;
            case BEACH:
                return WARM;
            case FOREST:
                return WARM;
            case TAIGA:
                return COLD;
            case EXTREME_HILLS:
                return COLD;
            case JUNGLE:
                return WARM;
            case MESA:
                return WARM;
            case SAVANNA:
                return WARM;
            case DESERT:
                return WARM;
            default:
                return NORMAL;
        }
    }
}
