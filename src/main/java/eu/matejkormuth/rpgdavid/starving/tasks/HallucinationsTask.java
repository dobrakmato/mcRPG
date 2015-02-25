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
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.Time;

public class HallucinationsTask extends RepeatingTask {
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (Data.of(p).isHallucinating()) {
                this.hallucinate(p);
            }
        }
    }

    private void hallucinate(Player p) {
        if (!p.hasPotionEffect(PotionEffectType.CONFUSION)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Time
                    .ofSeconds(10).toTicks(), 3));
        }

        if (Starving.getInstance().getRandom().nextInt(10) < 3) {
            if (!p.hasPotionEffect(PotionEffectType.BLINDNESS)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,
                        Time.ofSeconds(6).toTicks(), 3));
            }
        }

        p.playSound(p.getLocation(), Sound.PORTAL_TRAVEL, 1f, 1f);
        
        // TODO: Make more effects.
    }
}
