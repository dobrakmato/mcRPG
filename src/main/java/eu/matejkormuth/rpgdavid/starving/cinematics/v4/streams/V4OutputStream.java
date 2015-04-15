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
package eu.matejkormuth.rpgdavid.starving.cinematics.v4.streams;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.bukkit.Location;

public class V4OutputStream extends DataOutputStream {

    public V4OutputStream(OutputStream out) {
        super(out);
    }

    public void writeLocation(Location location) throws IOException {
        this.writeFloat((float) location.getX());
        this.writeFloat((float) location.getY());
        this.writeFloat((float) location.getZ());
        this.writeFloat(location.getYaw());
        this.writeFloat(location.getPitch());
        this.writeLong(location.getWorld().getUID().getMostSignificantBits());
        this.writeLong(location.getWorld().getUID().getLeastSignificantBits());
    }

}
