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
package eu.matejkormuth.rpgdavid.starving.cities;

import java.util.Collections;
import java.util.Set;

import eu.matejkormuth.rpgdavid.starving.Locality;
import eu.matejkormuth.rpgdavid.starving.Region;

public class City extends Locality {
    private CityType type;

    public City(String name, Region region) {
        super(name, region);
    }

    public CityType getType() {
        return this.type;
    }

    public void setType(CityType type) {
        this.type = type;
    }

    @Override
    public boolean isPvpFree() {
        return true;
    }

    public Set<Shop> getShops() {
        return Collections.emptySet();
    }
}
