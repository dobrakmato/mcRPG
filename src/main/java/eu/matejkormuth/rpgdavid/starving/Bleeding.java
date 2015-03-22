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
package eu.matejkormuth.rpgdavid.starving;

import org.bukkit.entity.Player;

public class Bleeding {
    private float flow;
    private int bleedingTicks;

    public Bleeding(float flow, int bleedingTicks) {
        this.flow = flow;
        this.bleedingTicks = bleedingTicks;
    }

    public Bleeding(float amount, long seconds) {
        this(amount / (seconds * 20), (int) seconds * 20);
    }

    public Bleeding(float amount, Time time) {
        this(amount / (time.toTicks()), (int) time.toTicks());
    }

    public int getBleedingTicks() {
        return this.bleedingTicks;
    }

    public float getFlow() {
        return this.flow;
    }

    public void start(Player player) {
        Data.of(player).setBleedingTicks(this.getBleedingTicks());
        Data.of(player).setBleedingFlow(this.flow);
    }
}
