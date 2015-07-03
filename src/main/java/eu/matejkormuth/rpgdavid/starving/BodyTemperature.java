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

public enum BodyTemperature {
    HYPOTHERMIA(32, "Hypothermia"),
    LOW_TEMPERATURE(35, "Low temperature"),
    NORMAL_TEMPERATURE(37, "Normal temperature"),
    HIGH_TEMPERATURE(39, "High temperature"),
    HYPERTHERMIA(42, "Hyperthermia");

    private String displayName;
    private float lowerLimit;

    private BodyTemperature(float lowerLimit, String displayName) {
        this.lowerLimit = lowerLimit;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public float getLowerLimit() {
        return this.lowerLimit;
    }

    public static BodyTemperature getByTemperature(float temperature) {
        if (temperature <= 32) {
            return HYPOTHERMIA;
        } else if (temperature <= 35) {
            return LOW_TEMPERATURE;
        } else if (temperature <= 37) {
            return NORMAL_TEMPERATURE;
        } else if (temperature <= 39) {
            return HIGH_TEMPERATURE;
        } else {
            return HYPERTHERMIA;
        }
    }
}
