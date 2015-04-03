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
package eu.matejkormuth.rpgdavid.starving.worldgen.filters;

import java.util.ArrayList;
import java.util.List;

public class FilterProperties implements Cloneable {

    private final List<FilterProperty> properties;

    public FilterProperties() {
        this.properties = new ArrayList<>();
    }

    public void add(FilterProperty property) {
        this.properties.add(property);
    }

    @Override
    public FilterProperties clone() {
        try {
            return (FilterProperties) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    };

    public List<FilterProperty> getProperties() {
        return properties;
    }

    /**
     * Returns FilterProperty or null if property of specified name does not
     * exists.
     * 
     * @param name
     *            Name of property to look up.
     * @return property for specified name if found, null otherwise
     */
    public FilterProperty getProperty(String name) {
        FilterProperty prop = null;
        for (int i = 0; i < this.properties.size(); i++) {
            prop = this.properties.get(i);
            if (prop.getName()
                    .equals(name)) {
                return prop;
            }
        }
        return null;
    }
}
