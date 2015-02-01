package eu.matejkormuth.rpgdavid.starving.sounds;

import org.bukkit.entity.Player;

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
public class Channel {
    private Atmosphere currentAtmosphere;
    private long lastStartTime;
    private final Player player;

    public Channel(final Player player) {
        this.player = player;
    }

    public void setLastStartTime(final long lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    public void setCurrentAtmosphere(Atmosphere currentAtmosphere) {
        this.currentAtmosphere = currentAtmosphere;
    }

    public long getLastStartTime() {
        return this.lastStartTime;
    }

    public Atmosphere getCurrentAtmosphere() {
        return this.currentAtmosphere;
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean isPlaying() {
        return this.lastStartTime + this.currentAtmosphere.getLength()
                - AmbientSoundManager.CROSSFADE_LENGTH > System
                    .currentTimeMillis();
    }

    public boolean shouldPlayNext() {
        return !this.isPlaying();
    }

    public void play(final Atmosphere atmosphere) {
        this.currentAtmosphere = atmosphere;
        this.currentAtmosphere.play(this.player);
        this.lastStartTime = System.currentTimeMillis();
    }
}
