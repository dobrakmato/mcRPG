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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import eu.matejkormuth.rpgdavid.spells.Spell;

public class HealingSpell extends Spell {
    private final static double RANGE = 3.0d;

    public HealingSpell() {
        super(Sound.LEVEL_UP, "Healing spell", 300);
    }

    @Override
    protected void cast0(Player invoker, Location location, Vector velocity) {
        invoker.setHealth(0.80 * invoker.getMaxHealth());
        invoker.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,
                20 * 10, 1));
        // Healing should be applied on all players in 3 block radius.
        for (Entity e : invoker.getNearbyEntities(RANGE, RANGE, RANGE)) {
            if (e instanceof Player) {
                ((Player) e).setHealth(0.80 * invoker.getMaxHealth());
                ((Player) e).addPotionEffect(new PotionEffect(
                        PotionEffectType.REGENERATION, 20 * 10, 1));
            }
        }
    }
}
