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

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.Scheduler;
import eu.matejkormuth.rpgdavid.starving.Time;

public class PlayerDeathListener implements Listener {
    private Map<Player, KillPlayerTask> tasks = new HashMap<>();
    
    @EventHandler
    private void onPlayerDeath(final EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {

            Bukkit.broadcastMessage("Debug: Player "
                    + event.getEntity().getName() + " died because "
                    + event.getCause());

            if (((Damageable) event.getEntity()).getHealth()
                    - event.getDamage() <= 0) {
                boolean canceled = this.shouldBeCanceled((Player) event
                        .getEntity());
                event.setCancelled(canceled);
            }
        }
    }

    @EventHandler
    private void onPlayerDeath(final PlayerDeathEvent event) {
        if(tasks.containsKey(event.getEntity())) {
            tasks.get(event.getEntity()).cancel();
            tasks.remove(event.getEntity());
        }
    }

    private boolean shouldBeCanceled(final Player p) {
        if (Data.of(p).isUnconscious()) {
            return false;
        } else {
            Data.of(p).setUnconscious(true);

            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Time
                    .ofMinutes(1).toTicks(), 4));

            // We may need to cancel this task if player dies while it's waiting
            // for execution.
            new KillPlayerTask(p);

            return true;
        }
    }

    private class KillPlayerTask implements Runnable {
        private Player player;
        private final int taskId;

        public KillPlayerTask(Player player) {
            this.player = player;
            // Schedule this task.
            this.taskId = Scheduler.delay(this, Time.ofMinutes(1));
        }

        public void cancel() {
            Bukkit.getScheduler().cancelTask(this.taskId);
        }

        @Override
        public void run() {
            this.player.damage(9999999D);
        }
    }
}
