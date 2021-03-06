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

import eu.matejkormuth.rpgdavid.starving.items.Mapping;

public abstract class ConsumableItem extends Item {
    private int foodLevelIncrement;
    private float saturationIncrement;

    public ConsumableItem(final int foodLevelIncrement,
            final float saturationIncrement, final Mapping mapping,
            final String name) {
        super(mapping, name);
        this.foodLevelIncrement = foodLevelIncrement;
        this.saturationIncrement = saturationIncrement;
    }

    public void onConsume(final Player player) {
        player.setFoodLevel(player.getFoodLevel() + this.foodLevelIncrement);
        player.setSaturation(player.getSaturation() + this.saturationIncrement);
        this.onConsume0(player);
    }

    public int getFoodLevelIncrement() {
        return this.foodLevelIncrement;
    }

    public float getSaturationIncrement() {
        return this.saturationIncrement;
    }

    protected void setFoodLevelIncrement(int foodLevelIncrement) {
        this.foodLevelIncrement = foodLevelIncrement;
    }

    protected void setSaturationIncrement(float saturationIncrement) {
        this.saturationIncrement = saturationIncrement;
    }

    protected abstract void onConsume0(Player player);
}
