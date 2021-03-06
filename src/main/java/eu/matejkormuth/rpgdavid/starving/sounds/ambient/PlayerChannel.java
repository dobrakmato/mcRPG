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

import java.util.Random;

import org.bukkit.entity.Player;

public class PlayerChannel {

    private Player player;
    private Random random;
    private Atmosphere currentAtmosphere;

    private long repeatingLastPlay;

    public PlayerChannel(Player player) {
        this.player = player;
        this.random = new Random();
    }

    public void setAtmosphere(Atmosphere atmosphere) {
        this.currentAtmosphere = atmosphere;
        this.playRepeating();
    }

    private void playRepeating() {
        repeatingLastPlay = timeStamp();
        for (RepeatingSound rs : currentAtmosphere.getRepeatingSounds()) {
            rs.play(this.player, this.player.getLocation(), Float.MAX_VALUE, 1);
        }
    }

    private void playRandom() {
       for (RandomSound rs : currentAtmosphere.getRandomSounds()) {
            if (random.nextFloat() <= rs.getChance()) {
                rs.play(this.player, this.player.getLocation(),
                        Float.MAX_VALUE, 1);
            }
        }
    }

    public void update() {
        this.playRandom();

        if (timeStamp() >= repeatingLastPlay + currentAtmosphere.getLength()
                - Atmosphere.CROSSFADE_LENGTH) {
            this.playRepeating();
        }
    }

    private static long timeStamp() {
        return System.currentTimeMillis();
    }

    public Atmosphere getAtmosphere() {
        return this.currentAtmosphere;
    }
}
