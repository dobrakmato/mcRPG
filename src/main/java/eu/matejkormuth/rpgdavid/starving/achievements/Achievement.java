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
package eu.matejkormuth.rpgdavid.starving.achievements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import eu.matejkormuth.rpgdavid.starving.Starving;

public abstract class Achievement implements Listener {
    private final String name;
    private final String description;
    private final int maxProgress;

    public Achievement(String name, String description) {
        this(name, description, 1);
    }

    public Achievement(String name, String description, int maxProgress) {
        this.name = name;
        this.description = description;
        this.maxProgress = maxProgress;

        Bukkit.getPluginManager().registerEvents(this, Starving.getInstance().getPlugin());
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    /**
     * Completes the achievement. Has same effect as calling progress(player,
     * getMaxProgress()).
     * 
     * @param player
     *            player
     */
    public void complete(Player player) {
        this.progress(player, this.maxProgress);
    }

    /**
     * Makes a progress in achievement for specified player by specified amount.
     * 
     * @param player
     *            player
     * @param amount
     *            amount
     */
    public void progress(Player player, int amount) {
        // TODO: Get last current progress.
        int currentProgress = 0;
        int nextProgress = Math.min(currentProgress + amount, this.maxProgress);

        // TODO: Save next current progress.

        if (nextProgress == this.maxProgress) {
            player.sendMessage(ChatColor.GREEN + "[ACHIEVEMENT] " + this.name + " completed!");
            this.onCompleted(player);
        }
    }

    /**
     * Gives reward (if any) to player.
     * 
     * @param player
     *            player to give reward to
     */
    private void onCompleted(Player player) {
    }
}
