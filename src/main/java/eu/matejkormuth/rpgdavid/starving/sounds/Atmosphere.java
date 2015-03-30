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

import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.Starving;

public class Atmosphere {
    private NamedSound[] sounds;

    public Atmosphere(NamedSound... sounds) {
        this.addSounds(sounds);
    }

    private void addSounds(NamedSound... sounds) {
        this.sounds = new NamedSound[sounds.length];
        for (int i = 0; i < sounds.length; i++) {
            this.sounds[i] = sounds[i];
            if (i > 0) {
                // Check length.
                if (sounds[i].getLength() != sounds[0].getLength()) {
                    throw new IllegalArgumentException(
                            "all sounds must have same lenght");
                }
            }
        }
    }

    public int getLength() {
        return this.sounds[0].getLength();
    }

    public void play(Player player) {
        for (NamedSound s : this.sounds) {
            Starving.NMS.playNamedSoundEffectMaxVolume(player, s.getSoundName(),
                    player.getLocation());
        }
    }
}
