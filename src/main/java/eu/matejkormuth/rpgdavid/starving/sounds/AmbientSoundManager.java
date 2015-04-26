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
package eu.matejkormuth.rpgdavid.starving.sounds;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.sounds.ambient.Atmosphere;
import eu.matejkormuth.rpgdavid.starving.sounds.ambient.PlayerChannel;
import eu.matejkormuth.rpgdavid.starving.sounds.ambient.RandomSound;
import eu.matejkormuth.rpgdavid.starving.sounds.ambient.RepeatingSound;

public class AmbientSoundManager {
    private Map<Player, PlayerChannel> channels;

    // Hardcoded atmospheres.
    public static final RandomSound[] NO_RANDOM = new RandomSound[] {};
    public static final Atmosphere WOODS = new Atmosphere("base", 9000,
            new RepeatingSound[] { new RepeatingSound(
                    "ambient.birdsonly") }, NO_RANDOM);
    public static final Atmosphere CAVE = new Atmosphere("cave", 9000,
            new RepeatingSound[] { new RepeatingSound(
                    "ambient.cave") }, NO_RANDOM);
 
    public AmbientSoundManager() {
        this.channels = new HashMap<Player, PlayerChannel>();

    }

    public void update() {
        for (PlayerChannel channel : this.channels.values()) {
            channel.update();
        }
    }

    public void addPlayer(Player player) {
        PlayerChannel ch = new PlayerChannel(player);
        this.channels.put(player, ch); 
        ch.setAtmosphere(WOODS);

    }

    public void removePlayer(Player player) {
        this.channels.remove(player);
    }
}
