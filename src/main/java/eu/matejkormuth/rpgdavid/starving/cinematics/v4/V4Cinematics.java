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

import java.nio.file.Path;

import eu.matejkormuth.rpgdavid.starving.cinematics.Cinematics;
import eu.matejkormuth.rpgdavid.starving.cinematics.Clip;
import eu.matejkormuth.rpgdavid.starving.cinematics.ClipPlayer;
import eu.matejkormuth.rpgdavid.starving.cinematics.Frame;
import eu.matejkormuth.rpgdavid.starving.cinematics.PlayerServer;

public class V4Cinematics extends Cinematics {

    private static final String IMPLEMENTATION_NAME = "V4";
    private PlayerServer server;

    public V4Cinematics() {
        this.server = new V4ClipPlayerServer();
    }

    @Override
    public String getImplementationName() {
        return IMPLEMENTATION_NAME;
    }

    @Override
    public Clip createClip() {
        return new V4Clip();
    }

    @Override
    public ClipPlayer createPlayer(Clip clip) {
        return new V4ClipPlayer((V4Clip) clip);
    }

    @Override
    public Frame createFrame() {
        return new V4Frame();
    }

    @Override
    public PlayerServer getServer() {
        return this.server;
    }

    @Override
    public Clip loadClip(Path file) {
        return V4Writer.load(file.toFile());
    }

    @Override
    public void saveClip(Clip clip, Path file) {
        if (clip instanceof V4Clip) {
            V4Writer.save((V4Clip) clip, file.toFile());
        }
    }

}
