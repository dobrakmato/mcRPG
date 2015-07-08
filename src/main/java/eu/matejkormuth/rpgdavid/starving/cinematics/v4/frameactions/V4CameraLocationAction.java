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
package eu.matejkormuth.rpgdavid.starving.cinematics.v4.frameactions;

import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.cinematics.frameactions.CameraLocationAction;
import eu.matejkormuth.rpgdavid.starving.cinematics.v4.streams.V4InputStream;
import eu.matejkormuth.rpgdavid.starving.cinematics.v4.streams.V4OutputStream;

public class V4CameraLocationAction extends AbstractAction implements
        CameraLocationAction {

    private Location location;

    @Override
    public void execute(Player player) {
        this.getClipPlayer().getCamera().setLocation(this.location);
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean isGlobal() {
        return true;
    }

    @Override
    public void writeTo(V4OutputStream os) throws IOException {
        os.writeLocation(this.location);
    }

    @Override
    public void readFrom(V4InputStream os) throws IOException {
        this.location = os.readLocation();
    }

}
