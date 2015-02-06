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
package eu.matejkormuth.rpgdavid.starving.chemistry;

import eu.matejkormuth.rpgdavid.starving.chemistry.chemicals.Acid;
import eu.matejkormuth.rpgdavid.starving.chemistry.chemicals.Alkali;
import eu.matejkormuth.rpgdavid.starving.chemistry.chemicals.Ethanol;

public final class Chemicals {
    private Chemicals() {
    }

    public static final Chemical ETHANOL = new Ethanol();
    public static final Chemical ACID = new Acid();
    public static final Chemical ALKALI = new Alkali();

    public static Chemical valueOf(String string) {
        switch (string.toLowerCase()) {
        case "acid":
            return Chemicals.ACID;
        case "ethanol":
            return Chemicals.ETHANOL;
        case "alkali":
            return Chemicals.ALKALI;
        default:
            return null;
        }
    }
}
