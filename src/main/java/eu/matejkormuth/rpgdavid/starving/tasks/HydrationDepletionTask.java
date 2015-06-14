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
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.BiomeTemperature;
import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.items.ClothingType;
import eu.matejkormuth.rpgdavid.starving.items.base.ClothingItem;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;

public class HydrationDepletionTask extends RepeatingTask {
    @Override
    public void run() {
        // Lower hydration by one each second.
        Data d = null;
        for (Player p : Bukkit.getOnlinePlayers()) {

            if (p.getGameMode() == Starving.ADMIN_MODE) {
                continue;
            }

            d = Data.of(p);

            float hydration = d.getHydrationLevel();

            switch (BiomeTemperature.byBiome(this.getBiomeOfPlayer(p))) {
                case COLD:
                    hydration -= 0.75F;
                    break;
                case NORMAL:
                    hydration -= 1F;
                    if (hasWinterClothing(p)) {
                        hydration -= 1F;
                    }

                    if (hasSummerHat(p)) {
                        hydration += 0.5F;
                    }
                    break;
                case WARM:
                    hydration -= 2F;
                    if (hasWinterClothing(p)) {
                        hydration -= 6F;
                    }

                    if (hasSummerHat(p)) {
                        hydration += 1.5F;
                    }
                    break;
            }

            d.setHydrationLevel(hydration);
        }
    }

    private boolean hasWinterClothing(Player p) {
        return isWinterClothing(p.getInventory().getChestplate())
                || isWinterClothing(p.getInventory().getLeggings())
                || isWinterClothing(p.getInventory().getBoots());
    }

    private boolean isWinterClothing(ItemStack is) {
        if (is == null) {
            return false;
        }

        Item item = Starving.getInstance().getItemManager().findItem(is);
        if (item instanceof ClothingItem) {
            return ((ClothingItem) item).getType() == ClothingType.WINTER_CLOTHING;
        }
        return false;
    }

    private boolean hasSummerHat(Player p) {
        if (p.getInventory().getHelmet() == null) {
            return false;
        }

        Item item = Starving.getInstance().getItemManager()
                .findItem(p.getInventory().getHelmet());
        if (item instanceof ClothingItem) {
            return ((ClothingItem) item).getType() == ClothingType.SUMMER_CLOTHING;
        }
        return false;
    }

    private Biome getBiomeOfPlayer(Player p) {
        return p.getLocation().getBlock().getBiome();
    }
}
