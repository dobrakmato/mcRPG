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
package eu.matejkormuth.rpgdavid.starving.locations;

public class City extends Locality {
    private CityState state;
    private boolean dynamic;

    public void setState(CityState state) {
        if (!this.dynamic) {
            throw new UnsupportedOperationException(
                    "Static cities can't change thier states.");
        }
        this.state = state;
    }

    public CityState getState() {
        return this.state;
    }

    public boolean isDynamic() {
        return this.dynamic;
    }

    public boolean isStatic() {
        return !this.dynamic;
    }
}
