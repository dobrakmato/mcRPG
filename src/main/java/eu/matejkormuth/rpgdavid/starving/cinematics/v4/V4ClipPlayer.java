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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.cinematics.Camera;
import eu.matejkormuth.rpgdavid.starving.cinematics.Clip;
import eu.matejkormuth.rpgdavid.starving.cinematics.ClipPlayer;
import eu.matejkormuth.rpgdavid.starving.cinematics.Frame;
import eu.matejkormuth.rpgdavid.starving.cinematics.FrameAction;

public class V4ClipPlayer implements ClipPlayer {
    private V4Camera camera;
    private V4Clip clip;
    private int currentFrame;
    private boolean playing;
    private List<ClipPlayerListener> listeners;

    public V4ClipPlayer(V4Clip clip) {
        this.listeners = new ArrayList<ClipPlayer.ClipPlayerListener>();
        this.clip = clip;
        this.camera = new V4Camera();
        this.currentFrame = 0;
        this.playing = false;
    }

    @Override
    public Camera getCamera() {
        return this.camera;
    }

    @Override
    public Clip getClip() {
        return this.clip;
    }

    public void addObserver(Player observer) {
        this.camera.addObserver(observer);
    }

    public void removeObserver(Player observer) {
        this.camera.removeObserver(observer);
    }

    @Override
    public void play() {
        this.playing = true;

        // Invoke all listeners.
        for (ClipPlayerListener listener : this.listeners) {
            listener.onPlay(this);
        }
    }

    @Override
    public void pause() {
        this.playing = false;

        // Invoke all listeners.
        for (ClipPlayerListener listener : this.listeners) {
            listener.onPause(this);
        }
    }

    @Override
    public void stop() {
        this.playing = false;
        this.currentFrame = 0;

        // Invoke all listeners.
        for (ClipPlayerListener listener : this.listeners) {
            listener.onStop(this);
        }
    }

    @Override
    public void nextFrame() {
        if (this.currentFrame == this.clip.getLength()) {
            this.stop();
        }

        Frame f = this.clip.getFrame(this.currentFrame);
        for (FrameAction fa : f.getActions()) {
            if (fa.isGlobal()) {
                fa.execute(null);
            } else {
                this.executeAction(fa);
            }
        }
        this.currentFrame++;
    }

    private void executeAction(FrameAction fa) {
        for (Player p : this.camera.getObservers()) {
            fa.execute(p);
        }
    }

    @Override
    public int getCurrentFrame() {
        return this.currentFrame;
    }

    @Override
    public int getLength() {
        return this.clip.getLength();
    }

    @Override
    public boolean isPlaying() {
        return this.playing;
    }

    @Override
    public void addListener(ClipPlayerListener listener) {
        this.listeners.add(listener);
    }
}
