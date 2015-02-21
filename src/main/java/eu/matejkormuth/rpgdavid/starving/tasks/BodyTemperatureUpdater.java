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
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.EnviromentType;
import eu.matejkormuth.rpgdavid.starving.persistence.IPersistable;
import eu.matejkormuth.rpgdavid.starving.persistence.Persist;
import eu.matejkormuth.rpgdavid.starving.persistence.PersistInjector;

public class BodyTemperatureUpdater extends RepeatingTask implements
        IPersistable {

    @Persist(key = "temperatureLowering")
    private float temperatureLowering = 0.05F;
    @Persist(key = "temperatureHighering")
    private float temperatureHighering = 0.05F;

    public BodyTemperatureUpdater() {
        this.reloadConfiguration();
    }

    @Override
    public void run() {
        Data d = null;
        for (Player p : Bukkit.getOnlinePlayers()) {
            d = Data.of(p);
            float temp = d.getBodyTemperature();

            switch (EnviromentType.byBiome(this.getBiomeOfPlayer(p))) {
                case COLD:
                    if (this.playerHasAnyClothing(p)) {
                        // Temperature is stable.
                    } else {
                        // Temperature is lowering.
                        temp -= temperatureLowering;
                    }
                    break;
                case NORMAL:
                    if (this.playerHasAnyClothing(p)) {
                        // Temperature is stable.
                    } else {
                        // Temperature is stable.
                    }
                    break;
                case WARM:
                    if (this.playerHasAnyClothing(p)) {
                        // Temperature is highering.
                        if (temp <= 39F) {
                            temp += temperatureHighering;
                        }
                    } else {
                        // Temperature is stable.
                    }
                    break;
            }

            d.setBodyTemperature(temp);
        }
    }

    private boolean playerHasAnyClothing(Player p) {
        return p.getInventory().getBoots() != null
                || p.getInventory().getChestplate() != null
                || p.getInventory().getLeggings() != null
                || p.getInventory().getHelmet() != null;
    }

    private Biome getBiomeOfPlayer(Player p) {
        return p.getLocation().getBlock().getBiome();
    }

    @Override
    public void reloadConfiguration() {
        PersistInjector.inject(this);
    }

    @Override
    public void saveConfiguration() {
        PersistInjector.store(this);
    }
}
