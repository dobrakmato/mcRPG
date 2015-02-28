/*
 *  mcRPG is a open source rpg bukkit/spigot plugin.
 *  Copyright (C) 2015 Matej Kormuth 
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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.cinematics.Camera;

public class V4Camera implements Camera {
    private List<Player> observers;
    private Location location;

    public V4Camera() {
        this.observers = new ArrayList<>();
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
