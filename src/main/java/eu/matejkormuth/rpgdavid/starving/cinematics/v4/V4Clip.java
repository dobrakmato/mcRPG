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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.matejkormuth.rpgdavid.starving.cinematics.Clip;
import eu.matejkormuth.rpgdavid.starving.cinematics.Frame;
import eu.matejkormuth.rpgdavid.starving.cinematics.v4.streams.V4InputStream;
import eu.matejkormuth.rpgdavid.starving.cinematics.v4.streams.V4OutputStream;

public class V4Clip implements Clip, V4Serializable {
    private int frameRate = 20;
    private List<V4Frame> frames;

    public V4Clip() {
        this.frames = new ArrayList<>();
    }

    @Override
    public void addFrame(Frame frame) {
        if (frame instanceof V4Frame) {
            this.frames.add((V4Frame) frame);
        }

        throw new RuntimeException(
                "Specified frame is not compatibile with V4Clip.");
    }

    @Override
    public Frame getFrame(int index) {
        return this.frames.get(index);
    }

    @Override
    public int getFrameRate() {
        return this.frameRate;
    }

    @Override
    public int getLength() {
        return this.frames.size();
    }

    @Override
    public void writeTo(V4OutputStream os) throws IOException {
        // Write frame rate.
        os.writeInt(this.frameRate);
        // Write frame count.
        os.writeShort(this.frames.size());
        // Write frames.
        for (V4Frame frame : this.frames) {
            frame.writeTo(os);
        }
    }

    @Override
    public void readFrom(V4InputStream os) throws IOException {
        // Read frame rate.
        this.frameRate = os.readInt();
        // Read frame count.
        short frameCount = os.readShort();
        this.frames = new ArrayList<V4Frame>(frameCount);
        // Read frames.
        for (int i = 0; i < frameCount; i++) {
            V4Frame frame = new V4Frame();
            frame.readFrom(os);
            this.frames.add(frame);
        }
    }

}
