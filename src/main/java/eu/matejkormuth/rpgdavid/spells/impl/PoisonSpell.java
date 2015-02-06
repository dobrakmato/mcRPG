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
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.util.Vector;

import eu.matejkormuth.rpgdavid.bukkitfixes.FlagMetadataValue;
import eu.matejkormuth.rpgdavid.spells.Spell;

public class PoisonSpell extends Spell {

    public PoisonSpell() {
        super(Sound.FIRE_IGNITE, "Poison spell", 100, 16000);
        this.setMinLevel(4);
    }

    @Override
    protected void cast0(Player invoker, Location location, Vector velocity) {
        ThrownPotion potion = (ThrownPotion) location.getWorld().spawnEntity(
                location, EntityType.SPLASH_POTION);

        potion.setMetadata("poisonSpell", new FlagMetadataValue());
        potion.setShooter(invoker);
        potion.setVelocity(velocity.multiply(2));
    }
}
