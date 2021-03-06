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

import eu.matejkormuth.rpgdavid.starving.cinematics.FrameAction;
import eu.matejkormuth.rpgdavid.starving.cinematics.v4.V4ClipPlayer;
import eu.matejkormuth.rpgdavid.starving.cinematics.v4.V4Serializable;

/**
 * Abstract action. Implements {@link V4Serializable} and {@link FrameAction}.
 * Contains useful field of {@link V4ClipPlayer}.
 */
public abstract class AbstractAction implements FrameAction, V4Serializable {

    private V4ClipPlayer clipPlayer;

    public V4ClipPlayer getClipPlayer() {
        return clipPlayer;
    }

    void setClipPlayer(V4ClipPlayer clipPlayer) {
        this.clipPlayer = clipPlayer;
    }
}
