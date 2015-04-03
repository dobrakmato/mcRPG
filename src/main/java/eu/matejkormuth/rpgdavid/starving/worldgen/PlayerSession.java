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
package eu.matejkormuth.rpgdavid.starving.worldgen;

import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.worldgen.brushes.Brush;
import eu.matejkormuth.rpgdavid.starving.worldgen.filters.Filter;
import eu.matejkormuth.rpgdavid.starving.worldgen.filters.FilterProperties;

public class PlayerSession {

    private Player player;
    private Brush brush;
    private Filter filter;
    private FilterProperties filterProperties;

    public PlayerSession(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
        // Clone properties from default properties.
        this.filterProperties = filter.getDefaultProperties().clone();
        // TODO: Notify remote client of filter properties change.
    }

    public Filter getFilter() {
        return filter;
    }

    public FilterProperties getFilterProperties() {
        return filterProperties;
    }

    public Brush getBrush() {
        return brush;
    }
}
