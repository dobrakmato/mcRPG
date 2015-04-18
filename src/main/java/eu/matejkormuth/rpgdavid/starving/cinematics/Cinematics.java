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
package eu.matejkormuth.rpgdavid.starving.cinematics;

import java.nio.file.Path;
import java.nio.file.Paths;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.cinematics.v4.V4Cinematics;

public abstract class Cinematics {

    private static Cinematics cinematics = new V4Cinematics();
    private static String cinematicsStorage = Starving.getInstance().getDataFolder().getAbsolutePath()
            + "/cinematics/";

    public static Cinematics getCinematics() {
        return cinematics;
    }

    public abstract String getImplementationName();

    public abstract Clip createClip();

    public abstract ClipPlayer createPlayer(Clip clip);

    public abstract Frame createFrame();

    public abstract PlayerServer getServer();

    public String getCinematicsFolder() {
        return cinematicsStorage;
    }

    public Path getCinematicFile(String... parts) {
        return Paths.get(cinematicsStorage, parts);
    }

    public abstract Clip loadClip(Path file);

    public Clip loadClip(String... parts) {
        return loadClip(getCinematicFile(parts));
    }

    public abstract void saveClip(Clip clip, Path file);

    public void saveClip(Clip clip, String... parts) {
        saveClip(clip, getCinematicFile(parts));
    }
}
