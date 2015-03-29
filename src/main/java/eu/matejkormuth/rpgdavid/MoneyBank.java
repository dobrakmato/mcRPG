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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import eu.matejkormuth.rpgdavid.money.Currencies;
import eu.matejkormuth.rpgdavid.money.Money;

public class MoneyBank implements Runnable {
    private static final String BANKS_DIR = "banks";

    private Map<UUID, Account> accounts;

    public MoneyBank() {
        this.accounts = new HashMap<>();
        this.deserialize();

        // To prevent data-loss we save all each minute.
        Bukkit.getScheduler().scheduleSyncRepeatingTask(
                RpgPlugin.getInstance(), this, 20L * 60, 20L * 60);
    }

    private void deserialize() {
        File[] files = RpgPlugin.getInstance().getFile(BANKS_DIR).listFiles();

        if (files == null) {
            throw new RuntimeException("Can't read banks directory!");
        }

        for (File file : files) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            UUID uuid = UUID.fromString(file.getName().replace(".yml", ""));
            Account a = new Account(uuid, new Money(
                    yaml.getInt("money.normal"), Currencies.NORMAL), new Money(
                    yaml.getInt("money.premium"), Currencies.PREMIUM));
            this.accounts.put(uuid, a);
        }
    }

    private void serialize() {
        // Save all.
        for (Account a : this.accounts.values()) {
            YamlConfiguration yaml = new YamlConfiguration();

            yaml.set("money.normal", a.getNormal().getAmount());
            yaml.set("money.premium", a.getPremium().getAmount());

            try {
                yaml.save(RpgPlugin.getInstance().getFile(BANKS_DIR,
                        a.getUUID().toString() + ".yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Account getAccount(OfflinePlayer player) {
        return this.getAccount(player.getUniqueId());
    }

    private Account getAccount(UUID uniqueId) {
        if (this.accounts.containsKey(uniqueId)) {
            return this.accounts.get(uniqueId);
        } else {
            return this.accounts.put(uniqueId, new Account(uniqueId));
        }
    }

    public void shutdown() {
        this.serialize();
    }

    public static class Account {
        private UUID uuid;
        private Money normal;
        private Money premium;

        public Account(UUID uuid) {
            this.uuid = uuid;
            this.normal = new Money(0, Currencies.NORMAL);
            this.premium = new Money(0, Currencies.PREMIUM);
        }

        public Account(UUID uuid, Money normal, Money premium) {
            this.uuid = uuid;
            this.normal = normal;
            this.premium = premium;
        }

        public Money getNormal() {
            return this.normal;
        }

        public Money getPremium() {
            return this.premium;
        }

        public UUID getUUID() {
            return this.uuid;
        }
    }

    @Override
    public void run() {
        this.serialize();
    }
}
