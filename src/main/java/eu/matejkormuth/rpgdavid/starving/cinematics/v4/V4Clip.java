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

import eu.matejkormuth.rpgdavid.starving.cinematics.Clip;
import eu.matejkormuth.rpgdavid.starving.cinematics.Frame;

public class V4Clip implements Clip {
    private int frameRate = 20;
    private List<V4Frame> frames;

    public V4Clip() {
        this.frames = new ArrayList<>();
    }

    @Override
    public Frame getFrame(int index) {
        return this.frames.get(index);
    }

    @Override
    public int getFrameRate() {
        return this.frameRate;
    }
}
