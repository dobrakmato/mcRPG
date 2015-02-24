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
package eu.matejkormuth.rpgdavid.starving.tasks;

import eu.matejkormuth.rpgdavid.starving.Starving;

public class TablistFooterClockTask extends RepeatingTask {
    private static final int MINECRAFT_DAY_LENGTH = 24000;
    private static final String FOOTER_LINK = "http://www.starving.eu";

    private TimeUpdater updater;

    private String text = "Test posuvneho textu ";

    public TablistFooterClockTask() {
        this.updater = Starving.getInstance().getRegistered(TimeUpdater.class);
    }

    @Override
    public void run() {
        String time = formatTime();
        text = shift(this.text);
        Starving.getInstance().setTabListFooter(
                FOOTER_LINK + " | " + time + " | " + text);
    }

    private static String shift(String s) {
        return s.charAt(s.length() - 1) + s.substring(0, s.length() - 1);
    }

    private String formatTime() {
        long timeOfDay = updater.getFullTime() % MINECRAFT_DAY_LENGTH;
        double time = timeOfDay / 1000;
        long hours = (long) Math.floor(time);
        long minutes = (long) time - hours * 60;
        if (hours < 10) {
            if (minutes < 10) {
                return "0" + hours + ":0" + minutes;
            } else {
                return "0" + hours + ":" + minutes;
            }
        } else {
            if (minutes < 10) {
                return hours + ":0" + minutes;
            } else {
                return hours + ":" + minutes;
            }
        }
    }
}
