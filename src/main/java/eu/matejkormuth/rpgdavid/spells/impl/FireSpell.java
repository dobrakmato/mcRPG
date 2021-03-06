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
package eu.matejkormuth.rpgdavid.spells.impl;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.bukkitfixes.FlagMetadataValue;
import eu.matejkormuth.rpgdavid.spells.Spell;

public class FireSpell extends Spell {
    public FireSpell() {
        super(Sound.FIRE_IGNITE, RpgPlugin.t("t_firespell"), 100, 5000);
        this.setMinLevel(1);
    }

    @Override
    protected void cast0(final Player invoker, final Location location,
            final Vector velocity) {
        Fireball fireball = (Fireball) location.getWorld().spawnEntity(
                location, EntityType.FIREBALL);

        // Set spawn time.
        fireball.setMetadata(
                "spawnedAt",
                new FixedMetadataValue(RpgPlugin.getInstance(), System
                        .currentTimeMillis()));

        fireball.setMetadata("fireSpell", new FlagMetadataValue());
        fireball.setShooter(invoker);
        fireball.setVelocity(velocity);
    }
}
