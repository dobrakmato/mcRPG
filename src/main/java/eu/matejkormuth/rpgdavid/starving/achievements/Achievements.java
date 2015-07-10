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
package eu.matejkormuth.rpgdavid.starving.achievements;

import eu.matejkormuth.rpgdavid.starving.achievements.death.Atlantis;
import eu.matejkormuth.rpgdavid.starving.achievements.death.Dynamite;
import eu.matejkormuth.rpgdavid.starving.achievements.death.FindingClownfish;
import eu.matejkormuth.rpgdavid.starving.achievements.death.Hades;
import eu.matejkormuth.rpgdavid.starving.achievements.death.HungerGames;
import eu.matejkormuth.rpgdavid.starving.achievements.death.IBelieveICanFly;
import eu.matejkormuth.rpgdavid.starving.achievements.death.Ikaros;
import eu.matejkormuth.rpgdavid.starving.achievements.death.OnADiet;
import eu.matejkormuth.rpgdavid.starving.achievements.death.Persephone;
import eu.matejkormuth.rpgdavid.starving.achievements.death.TheTorch;
import eu.matejkormuth.rpgdavid.starving.achievements.death.Zeus;
import eu.matejkormuth.rpgdavid.starving.achievements.kill.Ares;
import eu.matejkormuth.rpgdavid.starving.achievements.kill.Baptised;
import eu.matejkormuth.rpgdavid.starving.achievements.kill.HitLikeYouDo;
import eu.matejkormuth.rpgdavid.starving.achievements.kill.Mercenary;
import eu.matejkormuth.rpgdavid.starving.achievements.kill.Shooter;
import eu.matejkormuth.rpgdavid.starving.achievements.survival.DayOne;
import eu.matejkormuth.rpgdavid.starving.achievements.survival.DaySeven;
import eu.matejkormuth.rpgdavid.starving.achievements.survival.Hunger;
import eu.matejkormuth.rpgdavid.starving.achievements.survival.SoLong;

/**
 * Helper class for achievement singleton instances.
 * 
 * @see Achievement
 */
public class Achievements {

    public static final void setup() {
        // Register all achievements and setup listeners.
        register();
    }

    private static final void register() {
        // The list of all achievements follows.
        
        // Death achievements.
        register(new Atlantis());
        register(new Dynamite());
        register(new FindingClownfish());
        register(new Hades());
        register(new HungerGames());
        register(new IBelieveICanFly());
        register(new Ikaros());
        register(new OnADiet());
        register(new Persephone());
        register(new TheTorch());
        register(new Zeus());
        
        // Kill achievements.
        register(new Ares());
        register(new Baptised());
        register(new HitLikeYouDo());
        register(new Mercenary());
        register(new Shooter());
        
        // Survival achievements.
        register(new DayOne());
        register(new DaySeven());
        register(new Hunger());
        register(new SoLong());
    }

    private static void register(Achievement achievement) {
        // I don't know...
    }
}
