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

import org.bukkit.ChatColor;
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
    private final String coolDownId;
    private final int cooldown;

    public Spell(final Sound castSound, final String name, final int manaUsage,
            final int cooldown) {
        this(castSound, name, 1, 1, manaUsage, cooldown);
    }

    public Spell(final Sound castSound, final String name,
            final float castSoundPitch, final float castSoundVolume,
            final int manaUsage, final int cooldown) {
        this.castSound = castSound;
        this.name = name;
        this.coolDownId = "spell." + this.getClass().getSimpleName();
        this.castSoundPitch = castSoundPitch;
        this.castSoundVolume = castSoundVolume;
        this.manaUsage = manaUsage;
        this.cooldown = cooldown;
    }

    public String getName() {
        return this.name;
    }

    public Sound getCastSound() {
        return this.castSound;
    }

    public String getCoolDownId() {
        return coolDownId;
    }

    public int getManaUsage() {
        return manaUsage;
    }

    public void cast(final Player invoker) {
        // Spell must be cooled down.
        if (RpgPlugin.getInstance().getCooldowns()
                .isCooledDown(invoker, this.coolDownId)) {
            this.cast(invoker, invoker.getLocation(), invoker.getEyeLocation()
                    .getDirection());
            // Set cooldown.
            RpgPlugin.getInstance().getCooldowns()
                    .setCooldown(invoker, this.coolDownId, this.cooldown);
        } else {
            invoker.sendMessage(ChatColor.RED
                    + "This spell must be first cooled down! ("
                    + RpgPlugin.getInstance().getCooldowns()
                            .getTimeLeft(invoker, this.coolDownId) / 1000
                    + " seconds)");
        }
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
            this.cast0(
                    invoker,
                    location.add(velocity.clone().multiply(2)
                            .add(new Vector(0, 1, 0))), velocity);
        } else {
            invoker.sendMessage(ChatColor.RED + "Low mana!");
        }
    }

    protected abstract void cast0(final Player invoker,
            final Location location, final Vector velocity);
}
