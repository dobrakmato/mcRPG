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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.achievements.Achievement;
import eu.matejkormuth.rpgdavid.starving.events.time.MinuteTimeEvent;

public class SoLong extends Achievement {
    
    public SoLong() {
        super("Sooo long?", "Survived more then 30 days.");
    }
    
    @EventHandler
    private void onMinuteElapsed(final MinuteTimeEvent event) {
        Data data = null;
        for(Player p : Bukkit.getOnlinePlayers()) {
            data = Data.of(p);
            
            // By issue #25 day and night have 40 minutes. So 300 days is 40 * 30.
            if(data.getMinutesPlayed() >= 1200) {
                this.complete(p);
            }
        }
    }

}
