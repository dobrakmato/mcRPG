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
package eu.matejkormuth.rpgdavid.starving.listeners;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.Time;

public class PlayerDeathListener implements Listener {
    @EventHandler
    private void onPlayerDeath(final EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (((Damageable) event.getEntity()).getHealth()
                    - event.getDamage() <= 0) {
                boolean canceled = this.shouldBeCanceled((Player) event
                        .getEntity());
                event.setCancelled(canceled);
            }
        }

    }

    private boolean shouldBeCanceled(Player p) {
        if (Data.of(p).isUnconscious()) {
            return false;
        } else {
            Data.of(p).setUnconscious(true);

            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Time
                    .ofMinutes(1).toTicks(), 4));

            return true;
        }
    }
}
