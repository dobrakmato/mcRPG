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
package eu.matejkormuth.rpgdavid.starving.achievements.survival;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.starving.achievements.Achievement;

public class Hunger extends Achievement {
    
    private List<ItemStack> foodItems = new ArrayList<>();

    public Hunger() {
        super("Hunger!", "Tried the taste of post apocalyptic food.");
    }
    
    @EventHandler
    private void onItemConsume(final PlayerItemConsumeEvent event) {
        // This is completed by consuming a food.
        if(foodItems.contains(event.getItem())) {
            this.complete(event.getPlayer());
        }
    }

}
