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
package eu.matejkormuth.rpgdavid.starving.items.base;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;

import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.items.Category;
import eu.matejkormuth.rpgdavid.starving.items.Rarity;

public class DrinkItem extends ChemicalItem {
    private int staminaIncrement;
    private float hydrationIncrement;
    private int healthIncrement;
    private int infectionChance;

    public DrinkItem(String name, int staminaIncrement,
            float hydrationIncrement, int foodLevelIncrement,
            int healthIncrement, int infectionChanceInPercent) {
        super(name);
        this.staminaIncrement = staminaIncrement;
        this.hydrationIncrement = hydrationIncrement;
        this.setFoodLevelIncrement(foodLevelIncrement);
        this.healthIncrement = healthIncrement;
        this.infectionChance = infectionChanceInPercent;

        this.setCategory(Category.DRINKS);
        this.setRarity(Rarity.COMMON);
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }

    @Override
    protected void onConsume0(Player player) {
        Data d = Data.of(player);
        d.incrementStamina(staminaIncrement);
        d.incrementHydrationLevel(hydrationIncrement);

        this.incrementHealth(player);
        if (Starving.getInstance().getRandom().nextDouble() * 100 <= infectionChance) {
            d.setInfected(true);
        }
    }

    private void incrementHealth(Player player) {
        if (player.getHealth() + this.healthIncrement > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        } else {
            player.setHealth(player.getHealth() + this.healthIncrement);
        }
    }
}
