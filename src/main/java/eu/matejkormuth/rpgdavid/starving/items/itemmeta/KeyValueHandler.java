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

public interface KeyValueHandler {
	void set(String key, String value);

	default void set(String key, boolean value) {
		set(key, Boolean.toString(value));
	}

	default void set(String key, byte value) {
		set(key, Byte.toString(value));
	}

	default void set(String key, short value) {
		set(key, Short.toString(value));
	}

	default void set(String key, int value) {
		set(key, Integer.toString(value));
	}

	default void set(String key, long value) {
		set(key, Long.toString(value));
	}

	default void set(String key, float value) {
		set(key, Float.toString(value));
	}

	default void set(String key, double value) {
		set(key, Double.toString(value));
	}

	String get(String key);

	default byte getByte(String key) {
		return Byte.valueOf(get(key));
	}

	default short getShort(String key) {
		return Short.valueOf(get(key));
	}

	default int getInteger(String key) {
		return Integer.valueOf(get(key));
	}

	default long getLong(String key) {
		return Long.valueOf(get(key));
	}

	default float getFloat(String key) {
		return Float.valueOf(get(key));
	}

	default boolean getBoolean(String key) {
		return Boolean.valueOf(get(key));
	}

	default double getDouble(String key) {
		return Double.valueOf(get(key));
	}
}
