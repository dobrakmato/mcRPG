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
package eu.matejkormuth.rpgdavid.starving.persistence;

/**
 * <p>
 * Represents instance-less (or only one instance) object.
 * </p>
 * <p>
 * <b>This is used for classes that should be persistable but for some reason
 * can't extend {@link AbstractPersistable} abstract class.</b> AbstractPersistable abstract
 * class also implements this interface. This interface allows to have classes
 * that extends {@link AbstractPersistable} and classes that implements this interface
 * in same colelction to store their configuration at application shutdown easy.
 * </p>
 * <p>
 * You have to manually implement {@link PersistInjector#store(Object)} and
 * {@link PersistInjector#inject(Object)} methods or their overloads.
 * </p>
 *
 * @see AbstractPersistable
 * @see Persist
 * @see PersistInjector
 */
public interface Persistable {
    public void reloadConfiguration();

    public void saveConfiguration();
}
