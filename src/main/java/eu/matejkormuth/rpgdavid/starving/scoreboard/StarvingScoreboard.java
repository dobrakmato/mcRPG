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
package eu.matejkormuth.rpgdavid.starving.scoreboard;

import java.lang.ref.WeakReference;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.Data;

public class StarvingScoreboard extends OrderedScoreboard {
    private WeakReference<Player> player;

    public StarvingScoreboard(Player player) {
        super(10);
        this.player = new WeakReference<Player>(player);
        this.setTitle(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD
                + "STARVING");
    }

    public void update() {
        Player p = player.get();
        if (p != null) {
            this.updateTable(p);
        } else {
            this.destroy();
        }
    }

    private void updateTable(Player p) {
        Data d = Data.of(p);
        this.setLine(1, "Stamina: " + d.getStamina());
        this.setLine(2, "Hydration: " + d.getHydrationLevel());
        this.setLine(3, "Temperature: " + d.getBodyTemperature());
        this.setLine(4, "Blood: " + d.getBloodLevel());
        this.setLine(5, "Sick: " + d.isSick());
        this.setLine(6, "Infected: " + d.isInfected());
    }

    private void destroy() {
        this.rawScoreboard = null;
        this.scores = null;
        this.table.unregister();
    }
}
