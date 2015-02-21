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
package eu.matejkormuth.rpgdavid.starving;

import eu.matejkormuth.rpgdavid.starving.persistence.Persistable;

/**
 * <p>
 * This class provides useful constants for {@link Data} class. To allow easy
 * change of these default values, <i>these constants are loaded from
 * configuration file at server startup</i>.
 * </p>
 * <p>
 * This class is singleton because of {@link Persistable} implementation that
 * loads values from properties file during object construction.
 * </p>
 */
public class DataDefaults extends Persistable {
    private static DataDefaults instance;

    /**
     * Returns the only instance of {@link DataDefaults} available.
     * 
     * @return instance of DataDeafults
     */
    public static final DataDefaults get() {
        if (instance == null) {
            return instance = new DataDefaults();
        } else {
            return instance;
        }
    }

    private DataDefaults() {
        super();
    }
    
    // TODO: Add all deafults.
}