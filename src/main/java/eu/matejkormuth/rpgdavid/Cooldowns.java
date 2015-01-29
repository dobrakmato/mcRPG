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
package eu.matejkormuth.rpgdavid;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Cooldowns implements Runnable {
    private static final long CACHE_TIMEOUT = 1000 * 60 * 10; // 10 minutes.
    private Map<UUID, CooldownEntry> cooldowns;

    public Cooldowns() {
        this.cooldowns = new HashMap<UUID, Cooldowns.CooldownEntry>();
    }

    public boolean isCooledDown(Player player, String thing) {
        if (this.cooldowns.containsKey(player.getUniqueId())) {
            return this.cooldowns.get(player.getUniqueId()).get(thing) < System
                    .currentTimeMillis();
        }
        return true;
    }

    public void setCooldown(Player player, String thing, long cooldownLengthInMs) {
        if (cooldownLengthInMs > CACHE_TIMEOUT) {
            throw new IllegalArgumentException(
                    "cooldownLength must be less than CACHE_TIMEOUT = "
                            + CACHE_TIMEOUT);
        }

        if (!this.cooldowns.containsKey(player.getUniqueId())) {
            this.cooldowns.put(player.getUniqueId(), new CooldownEntry());
        }
        this.cooldowns.get(player.getUniqueId()).set(thing, cooldownLengthInMs);
    }

    public void removeCooldowns(Player player) {
        this.cooldowns.remove(player.getUniqueId());
    }

    public void cleanUp() {
        for (Iterator<CooldownEntry> iterator = this.cooldowns.values()
                .iterator(); iterator.hasNext();) {
            CooldownEntry entry = iterator.next();
            if (entry.lastAccessed + CACHE_TIMEOUT < System.currentTimeMillis()) {
                iterator.remove();
            }
        }
    }

    @Override
    public void run() {
        this.cleanUp();
    }

    public long getTimeLeft(Player player, String thing) {
        if (!this.cooldowns.containsKey(player.getUniqueId())) {
            return 0;
        }
        return this.cooldowns.get(player.getUniqueId()).get(thing)
                - System.currentTimeMillis();
    }

    private class CooldownEntry {
        private long lastAccessed;
        private Map<String, Long> cooledDownAt;

        public CooldownEntry() {
            this.cooledDownAt = new HashMap<String, Long>();
        }

        private long get(String id) {
            if (this.cooledDownAt.containsKey(id)) {
                return this.cooledDownAt.get(id).longValue();
            }
            return 0;
        }

        public void set(String id, long cooldownLength) {
            this.lastAccessed = System.currentTimeMillis();
            this.cooledDownAt.put(id, this.lastAccessed + cooldownLength);
        }
    }
}
