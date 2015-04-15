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
package eu.matejkormuth.rpgdavid.starving.cinematics.v4;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.cinematics.Camera;

public class V4Camera implements Camera {
    private Set<Player> observers;
    private Location location;

    public V4Camera() {
        this.observers = new HashSet<>();
    }

    @Override
    public void addObserver(Player observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Player observer) {
        this.observers.remove(observer);
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public Collection<Player> getObservers() {
        return this.observers;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
        this.broadcastNewLoc();
    }

    private void broadcastNewLoc() {
        for (Player observer : this.observers) {
            observer.teleport(this.location);
        }
    }

}
