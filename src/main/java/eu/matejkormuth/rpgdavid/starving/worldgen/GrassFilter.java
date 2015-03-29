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

/**
 * <p>
 * Generates grass on top of grass blocks with specified proeprties (eg. whether
 * to generate flowers).
 * </p>
 * <p>
 * Properties:
 * <ul>
 * <li>cover: 0.00 (0%) to 1.00 (100%)</li>
 * <li>longGrass: false / true</li>
 * <li>flowers: false / true</li>
 * <li>grass type: fern / grass / dead bush / all
 * <li>clear existing grass: false / true</li>
 * </ul>
 * </p>
 * 
 * @author Matej Kormuth
 */
public class GrassFilter implements Filter {

    @Override
    public void apply(CircleAffectedBlocksDef region, Location center) {
        // TODO Auto-generated method stub
        Filter.super.apply(region, center);
    }

}
