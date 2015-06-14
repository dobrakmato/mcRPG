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
package eu.matejkormuth.rpgdavid.starving.items.melee;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.items.Mappings;
import eu.matejkormuth.rpgdavid.starving.items.Rarity;
import eu.matejkormuth.rpgdavid.starving.items.base.MeleeWeapon;

public class Knife extends MeleeWeapon {

    public Knife() {
        super(Mappings.KNIFE, "Knife", 4, 1);
        this.setRarity(Rarity.COMMON);
        this.setMaxStackAmount(12);
    }

    @Override
    public void onAttack(Player damager, LivingEntity entity, double damage) {
        // If player is behind the zombie, he get 20 HP damage bonus.

        float yawDiff = damager.getEyeLocation().getYaw()
                - entity.getLocation().getYaw();
        if (Math.abs(yawDiff) < 15f) {
            // This is insta-kill from behind.
            super.onAttack(damager, entity, 20);
        } else {
            // We have still 40% chance to give insta-kill.
            if (Math.random() > .6f) {
                super.onAttack(damager, entity, 20);
            } else {
                super.onAttack(damager, entity, damage);
            }
        }
    }
}
