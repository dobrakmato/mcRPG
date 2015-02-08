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

public class AmbientSoundManager {
    public static final long CROSSFADE_LENGTH = 750L; // 750 ms

    private Configuration configuration;

    private Map<Player, Channel> channels;

    public AmbientSoundManager() {
        this.configuration = new Configuration();
        this.channels = new HashMap<Player, Channel>();
    }

    public void update() {
        for (Channel channel : this.channels.values()) {
            if (channel.shouldPlayNext()) {
                // Find right atmosphere and play it..
                channel.play(this.configuration.getAtmosphere(channel
                        .getPlayer()));
            }
        }
    }

    public void addPlayer(Player player) {
        this.channels.put(player, new Channel(player));
    }

    public void removePlayer(Player player) {
        this.channels.remove(player);
    }

    private class Configuration {
        private Atmosphere getAtmosphere(final Player player) {
            // TODO: Implement getting atmosphere by enviroment.
            return null;
        }
    }
}
