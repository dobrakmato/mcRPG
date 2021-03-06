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

import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.Locality;
import eu.matejkormuth.rpgdavid.starving.Starving;

public class LocalityTeller extends RepeatingTask {
    private Map<Player, Locality> lastLocalityMap;

    public LocalityTeller() {
        this.lastLocalityMap = new WeakHashMap<Player, Locality>();
    }

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Locality loc = Starving.getInstance().getLocality(p.getLocation());
            if (this.lastLocalityMap.get(p) != loc) {
                p.playSound(p.getLocation(), Sound.AMBIENCE_CAVE, 1, 1);
                Starving.NMS.sendTitle(p, loc, 300, 300, 700);
                this.lastLocalityMap.put(p, loc);
            }
        }
    }
}
