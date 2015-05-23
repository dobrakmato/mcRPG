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
package eu.matejkormuth.rpgdavid.starving.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.Time;

public class BloodLevelConsuquencesTask extends RepeatingTask {
    // private Map<Player, Long> lastDamage = new HashMap<>();

    @Override
    public void run() {
        float amount = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {

            if (p.getGameMode() == Starving.ADMIN_MODE) {
                continue;
            }

            amount = Data.of(p).getBloodLevel();

            if (amount < 2000) {
                // Instant dead.
                this.instantDeath(p);
            } else if (amount < 2500) {
                // Can't run, fight. He dies 2HP/min, constant CONFUSION

                this.constatntConfusion(p);
                Data.of(p).setAbleToSprint(false);
            } else if (amount < 3000) {
                // Can't run, -75% dmg, dying 1HP/min, constant CONFUSION

                this.constatntConfusion(p);
                Data.of(p).setAbleToSprint(false);
            } else if (amount < 4000) {
                // Can't run, -25% dmg, periodic CONFUSION

                this.periodicConfusion(p);
                Data.of(p).setAbleToSprint(false);
            } else if (amount < 4500) {
                // Stamina has 1/6 capacity.
            } else {
                // Reset to defaults.
                Data.of(p).setAbleToSprint(true);
            }
        }
    }

    private void periodicConfusion(Player p) {
        // Currently simulated via low probability.
        if (Starving.getInstance().getRandom().nextDouble() * 10 < 1) {
            if (!p.hasPotionEffect(PotionEffectType.CONFUSION)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,
                        Time.ofSeconds(30).toTicks(), 1));
            }
        }
    }

    private void constatntConfusion(Player p) {
        if (!p.hasPotionEffect(PotionEffectType.CONFUSION)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Time
                    .ofMinutes(15).toTicks(), 1));
        }
    }

    private void instantDeath(Player p) {
        p.damage(99999D);
    }
}
