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
package eu.matejkormuth.rpgdavid.starving.sounds.ambient;

public class Atmosphere {

    /**
     * Length of atmosphere crossfades in milliseconds.
     */
    public static final long CROSSFADE_LENGTH = 750;

    private RepeatingSound[] repeatingSounds;
    private RandomSound[] randomSounds;
    private int length;
    private String name;

    public Atmosphere(String name, RepeatingSound[] repeatingSounds,
            RandomSound[] randomSounds) {
        this.repeatingSounds = repeatingSounds;
        this.randomSounds = randomSounds;
        this.name = name;
    }

    public long getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public RandomSound[] getRandomSounds() {
        return randomSounds;
    }

    public RepeatingSound[] getRepeatingSounds() {
        return repeatingSounds;
    }
}
