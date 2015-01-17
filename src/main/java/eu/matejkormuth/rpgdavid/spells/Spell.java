/*
 *  mcRPG is a open source rpg bukkit/spigot plugin.
 *  Copyright (C) 2015 Matej Kormuth 
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
package eu.matejkormuth.rpgdavid.spells;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import eu.matejkormuth.rpgdavid.RpgPlugin;

public abstract class Spell {
    private final Sound castSound;
    private final float castSoundPitch;
    private final float castSoundVolume;
    private final String name;
    private final int manaUsage;

    public Spell(final Sound castSound, final String name, final int manaUsage) {
        this(castSound, name, 1, 1, manaUsage);
    }

    public Spell(final Sound castSound, final String name,
            final float castSoundPitch, final float castSoundVolume,
            final int manaUsage) {
        this.castSound = castSound;
        this.name = name;
        this.castSoundPitch = castSoundPitch;
        this.castSoundVolume = castSoundVolume;
        this.manaUsage = manaUsage;
    }

    public String getName() {
        return this.name;
    }

    public Sound getCastSound() {
        return this.castSound;
    }

    public int getManaUsage() {
        return manaUsage;
    }

    public void cast(final Player invoker) {
        this.cast(invoker, invoker.getLocation(), invoker.getEyeLocation()
                .getDirection());
    }

    public void cast(final Player invoker, final Location location,
            final Vector velocity) {
        // Take mana from player if he has enough.
        if (RpgPlugin.getInstance().getProfile(invoker)
                .takeMana(this.manaUsage)) {
            // Play sound effect.
            location.getWorld().playSound(location, this.castSound,
                    this.castSoundVolume, this.castSoundPitch);
            // Cast the spell.
            this.cast0(invoker, location, velocity);
        }
    }

    protected abstract void cast0(final Player invoker,
            final Location location, final Vector velocity);
}
