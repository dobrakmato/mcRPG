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

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreboardsList extends ArrayList<PlayerStatsScoreboard> implements
        Runnable {
    private static final long serialVersionUID = 1L;

    @Override
    public boolean add(final PlayerStatsScoreboard e) {
        e.getPlayer().setScoreboard(e.getScoreboard());
        return super.add(e);
    }

    @Override
    public boolean remove(final Object o) {
        if (o instanceof PlayerStatsScoreboard) {
            ((PlayerStatsScoreboard) o).getPlayer().setScoreboard(
                    Bukkit.getScoreboardManager().getNewScoreboard());
        }
        return super.remove(o);
    }

    public void remove(final Player player) {
        for (PlayerStatsScoreboard p : this) {
            if (p.getPlayer().equals(player)) {
                this.remove(p);
                return;
            }
        }
    }

    @Override
    public void run() {
        for (PlayerStatsScoreboard p : this) {
            p.update();
        }
    }
}
