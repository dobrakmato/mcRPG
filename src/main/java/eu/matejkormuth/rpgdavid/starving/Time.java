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

public class Time {
    private long milis;

    private Time(long l) {
        this.milis = l;
    }

    public static Time ofMinutes(long minutes) {
        return new Time(minutes * 60 * 1000);
    }

    public static Time ofTicks(long ticks) {
        return new Time(ticks / 20 * 1000);
    }

    public static Time ofSeconds(long seconds) {
        return new Time(seconds * 1000);
    }

    public long toMiliseconds() {
        return this.milis;
    }

    public long toSeconds() {
        return this.milis / 1000;
    }

    public int toTicks() {
        return (int) (this.milis / 1000 * 20);
    }
    
    public long toLongTicks() {
        return this.milis / 1000 * 20;
    }
}
