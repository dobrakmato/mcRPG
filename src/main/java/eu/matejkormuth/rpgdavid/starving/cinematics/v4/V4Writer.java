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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import eu.matejkormuth.rpgdavid.starving.cinematics.v4.streams.V4InputStream;
import eu.matejkormuth.rpgdavid.starving.cinematics.v4.streams.V4OutputStream;

public class V4Writer {

    public static void save(V4Clip clip, File file) {
        try {
            clip.writeTo(new V4OutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static V4Clip load(File file) {
        V4Clip clip = new V4Clip();
        try {
            clip.readFrom(new V4InputStream(new FileInputStream(file)));
            return clip;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
