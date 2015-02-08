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
package eu.matejkormuth.rpgdavid.starving.chemistry.chemicals;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.chemistry.Chemical;

public class Acid extends Chemical {
    public Acid() {
        super("Acid");
    }

    @Override
    public void onPureConsumedBy(final Player player, float amount) {
        if (amount < 1) {
            // Low amount.
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,
                    20 * 120, 2));
        } else if (amount < 18) {
            // Medium amount - damage.
            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON,
                    20, 0));
            player.damage(amount / 2 - 2D);
        } else {
            // High amount - death.
            player.damage(amount / 2 - 2D);
            // Slow death.
            Bukkit.getScheduler().scheduleSyncDelayedTask(
                    Starving.getInstance().getPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            player.addPotionEffect(new PotionEffect(
                                    PotionEffectType.POISON, 20 * 240, 1));
                        }
                    }, 20 * 10L);
            Bukkit.getScheduler().scheduleSyncDelayedTask(
                    Starving.getInstance().getPlugin(), new Runnable() {
                        @Override
                        public void run() {
                            player.damage(6D);
                        }
                    }, 20 * 30L);
        }
    }
}
