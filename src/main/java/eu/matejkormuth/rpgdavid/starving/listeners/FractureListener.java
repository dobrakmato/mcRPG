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

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.matejkormuth.rpgdavid.starving.Bleeding;
import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.Time;

public class FractureListener implements Listener {
    @EventHandler
    private void onFallDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == DamageCause.FALL) {
                this.makeFracture((Player) event.getEntity(), event.getDamage());
            }
        }
    }

    private void makeFracture(Player player, double damage) {
        if (damage > 13) {
            this.makeFatalFracture(player);
        } else if (damage > 11) {
            this.makeComplexFractureLevel2(player);
        } else if (damage > 9) {
            this.makeComplexFractureLevel1(player);
        } else if (damage > 7) {
            this.makeNormalFractureLevel2(player);
        } else if (damage > 5) {
            this.makeNormalFractureLevel1(player);
        } else if (damage > 3) {
            this.makeSimpleFracture(player);
        } else if (damage > 1) {
            this.makeShortTermPain(player);
        }
    }

    private void makeFatalFracture(Player player) {
        // 100% probability
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
                (int) Time.ofMinutes(20).toTicks(), 6));

        new Bleeding(3000, Time.ofMinutes(2)).start(player);
    }

    private void makeComplexFractureLevel2(Player player) {
        // 70% probability
        if (Starving.getInstance().getRandom().nextDouble() * 10 < 7) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
                    (int) Time.ofMinutes(20).toTicks(), 4));

            new Bleeding(2500, Time.ofMinutes(10)).start(player);
        }
    }

    private void makeComplexFractureLevel1(Player player) {
        // 70% probability
        if (Starving.getInstance().getRandom().nextDouble() * 10 < 7) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
                    (int) Time.ofMinutes(20).toTicks(), 2));

            new Bleeding(2000, Time.ofMinutes(10)).start(player);
        }
    }

    private void makeNormalFractureLevel2(Player player) {
        // 45% probability
        if (Starving.getInstance().getRandom().nextDouble() * 10 < 4.5) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
                    (int) Time.ofMinutes(15).toTicks(), 1));

            new Bleeding(1500, Time.ofMinutes(20)).start(player);
        }
    }

    private void makeNormalFractureLevel1(Player player) {
        // 45% probability
        if (Starving.getInstance().getRandom().nextDouble() * 10 < 4.5) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
                    (int) Time.ofMinutes(10).toTicks(), 1));

            new Bleeding(1000, Time.ofMinutes(20)).start(player);
        }
    }

    private void makeSimpleFracture(Player player) {
        // 25% probability
        if (Starving.getInstance().getRandom().nextDouble() * 10 < 2.5) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
                    (int) Time.ofMinutes(5).toTicks(), 0));

            new Bleeding(500, Time.ofMinutes(20)).start(player);
        }
    }

    private void makeShortTermPain(Player player) {
        // 15% probability
        if (Starving.getInstance().getRandom().nextDouble() * 10 < 1.5) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
                    (int) Time.ofSeconds(30).toTicks(), 0));
        }
    }
}
