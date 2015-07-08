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
package eu.matejkormuth.rpgdavid.starving.worldgen.filters.base;

import java.util.HashMap;
import java.util.Map;

public class FilterProperties implements Cloneable {

    private final Map<String, FilterProperty> properties;
    private boolean locked = false;

    public FilterProperties() {
        this.properties = new HashMap<>();
    }

    public void set(FilterProperty property) {
        if (locked) {
            throw new RuntimeException(
                    "Can't modify property of locked (immutable) FilterProperties! Use FilterProperties::clone().");
        }
        this.properties.put(property.getName(), property);
    }

    @Override
    public FilterProperties clone() {
        try {
            FilterProperties fp = (FilterProperties) super.clone();
            fp.locked = false;
            return fp;
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    };

    public Map<String, FilterProperty> getProperties() {
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
        return properties.get(name);
    }

    public void lock() {
        this.locked = true;
    }
}
