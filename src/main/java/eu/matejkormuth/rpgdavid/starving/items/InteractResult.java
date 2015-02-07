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
package eu.matejkormuth.rpgdavid.starving.items;

public final class InteractResult {
    private static final InteractResult ONE = new InteractResult(1);
    private static final InteractResult NONE = new InteractResult(0);
    private static final InteractResult ALL = new InteractResult(-1);

    private int used = 0;

    private InteractResult(final int amount) {
        this.used = amount;
    }

    public final boolean isUsed() {
        return this.used != 0;
    }

    public final int getUsedAmount() {
        return this.used;
    }

    public static final InteractResult useOne() {
        return ONE;
    }

    public static final InteractResult useNone() {
        return NONE;
    }

    public static final InteractResult useAll() {
        return ALL;
    }

    public static final InteractResult useAmount(int amount) {
        return new InteractResult(amount);
    }
}
