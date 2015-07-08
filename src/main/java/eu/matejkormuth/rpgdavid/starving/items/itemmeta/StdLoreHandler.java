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
package eu.matejkormuth.rpgdavid.starving.items.itemmeta;

import java.util.List;

public class StdLoreHandler implements KeyValueHandler {

    private static final char SEPARATOR = ':';
    private LoreAccessor accessor;

    public StdLoreHandler(LoreAccessor loreAccessor) {
        this.accessor = loreAccessor;
    }

    @Override
    public void set(String key, String value) {
        List<String> lore = accessor.getLore();
        for (int i = 0; i < lore.size(); i++) {
            String line = lore.get(i);
            if (line.startsWith(key + SEPARATOR)) {
                lore.set(i, key + SEPARATOR + " " + value);
                accessor.setLore(lore);
                return;
            }
        }
        lore.add(key + SEPARATOR + " " + value);
        accessor.setLore(lore);
    }

    @Override
    public String get(String key) {
        for (String line : accessor.getLore()) {
            if (line.startsWith(key + SEPARATOR)) {
                return line.substring(line.indexOf(SEPARATOR) + 1).trim();
            }
        }
        return null;
    }
}
