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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.starving.persistence.Persist;
import eu.matejkormuth.rpgdavid.starving.persistence.PersistInjector;

public class Data {
    private static final Map<Player, Data> cached;

    static {
        cached = new HashMap<>();
    }

    private static File dataFileOf(Player player) {
        return RpgPlugin.getInstance().getFile("pdatas",
                player.getUniqueId().toString() + ".data");
    }

    public static Data of(Player player) {
        if (cached.containsKey(player)) {
            return cached.get(player);
        } else {
            File f = null;
            if ((f = dataFileOf(player)).exists()) {
                return new Data(f);
            } else {
                return new Data(player);
            }
        }
    }

    private Player player;

    @Persist(key = "bleedingTicks")
    private int bleedingTicks = 0;
    @Persist(key = "bleedingFlow")
    private float bleedingFlow = 0;
    @Persist(key = "bloodLevel")
    private float bloodLevel;

    private Data(Player player) {
        this.player = player;
    }

    public Data(File f) {
        PersistInjector.inject(this, f);
    }

    public Data save() {
        PersistInjector.store(this, dataFileOf(this.player));
        return this;
    }

    public Data uncache() {
        cached.remove(this.player);
        return this;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getBleedingTicks() {
        return this.bleedingTicks;
    }

    public void setBleedingTicks(int bleedingTicks) {
        this.bleedingTicks = bleedingTicks;
    }

    public void decrementBleedingTicks() {
        this.bleedingTicks--;
    }

    public float getBleedingFlow() {
        return this.bleedingFlow;
    }

    public void setBleedingFlow(float bleedingFlow) {
        this.bleedingFlow = bleedingFlow;
    }

    public float getBloodLevel() {
        return this.bloodLevel;
    }

    public void setBloodLevel(float bloodLevel) {
        this.bloodLevel = bloodLevel;
    }

    public boolean isBleeding() {
        return this.bleedingTicks > 0;
    }

}
