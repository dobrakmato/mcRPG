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
import eu.matejkormuth.rpgdavid.starving.worldgen.brushes.BrushType;
import eu.matejkormuth.rpgdavid.starving.worldgen.brushes.CircleBrush;
import eu.matejkormuth.rpgdavid.starving.worldgen.brushes.SquareBrush;
import eu.matejkormuth.rpgdavid.starving.worldgen.filters.GrassFilter;
import eu.matejkormuth.rpgdavid.starving.worldgen.filters.base.Filter;
import eu.matejkormuth.rpgdavid.starving.worldgen.filters.base.FilterProperties;

public class PlayerSession {

    private Player player;
    private Brush brush;
    private Filter filter;
    private FilterProperties filterProperties;
    private int maxDistance;

    public PlayerSession(Player player) {
        this.player = player;
        this.maxDistance = 256;
        this.brush = new CircleBrush(5);
        this.setFilter(new GrassFilter());
    }

    public Player getPlayer() {
        return player;
    }

    public void setBrushSize(int newSize) {
        if (this.brush.getType() == BrushType.CIRCLE) {
            this.brush = new CircleBrush(newSize);
        } else if (this.brush.getType() == BrushType.SQUARE) {
            this.brush = new SquareBrush(newSize);
        } else {
            throw new RuntimeException("Invalid brush type!");
        }
    }

    public void setBrushType(BrushType type) {
        int size = this.brush.getSize();
        switch (type) {
            case CIRCLE:
                this.brush = new CircleBrush(size);
                break;
            case SQUARE:
                this.brush = new SquareBrush(size);
                break;
        }
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

    public void setFilterProperties(FilterProperties filterProperties) {
        this.filterProperties = filterProperties;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public int getMaxDistance() {
        return this.maxDistance;
    }

    public Brush getBrush() {
        return brush;
    }
}
