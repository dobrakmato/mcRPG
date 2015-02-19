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
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.starving.persistence.Persist;
import eu.matejkormuth.rpgdavid.starving.persistence.PersistInjector;
import eu.matejkormuth.rpgdavid.starving.persistence.Persistable;

/**
 * <p>
 * Holds all information about Starving player. Bettern name would be
 * <b>PlayerData</b>, but that's too long or <b>PData</b>, but that's too
 * confusing. <i>Fields of this class are serialized using
 * {@link PersistInjector} although the class does not extend
 * {@link Persistable}</i>.
 * </p>
 * <p>
 * You can obtain player data by calling static {@link Data#of(Player)} method
 * which returns {@link Data} object for specified Player.
 * </p>
 * <p>
 * Example usage will be:
 * 
 * <pre>
 * Data.of(player).setInfected(true);
 * </pre>
 * 
 * </p>
 */
public class Data {
    private static final Map<Player, Data> cached;

    static {
        cached = new HashMap<>();
    }

    private static File dataFileOf(OfflinePlayer player) {
        return RpgPlugin.getInstance().getFile("pdatas",
                player.getUniqueId().toString() + ".data");
    }

    /**
     * <p>
     * Returns Data object related to specified player. This method first checks
     * if Data object is cached. If not, it either loads data from harddisk or
     * creates a new default Data object.
     * </p>
     * This method never returns null.
     * 
     * @param player
     *            player object of which data soud be loaded
     * @return Data object which holds all informations related to this player
     */
    public static Data of(OfflinePlayer player) {
        if (cached.containsKey(player)) {
            return cached.get(player);
        } else {
            File f = null;
            if ((f = dataFileOf(player)).exists()) {
                return loadData(f);
            } else {
                return createData(player);
            }
        }
    }

    private static Data loadData(File f) {
        return new Data(f);
    }

    private static Data createData(OfflinePlayer player) {
        return new Data(player);
    }

    private OfflinePlayer player;

    @Persist(key = "bleedingTicks")
    private int bleedingTicks = 0;
    @Persist(key = "bleedingFlow")
    private float bleedingFlow = 0;
    @Persist(key = "bloodLevel")
    private float bloodLevel = 5000;

    @Persist(key = "stamina")
    private float stamina = 800;
    @Persist(key = "staminaCapacity")
    private float staminaCapacity = 800;

    @Persist(key = "bodyTemperature")
    private float bodyTemperature;

    @Persist(key = "infected")
    private boolean infected = false;

    @Persist(key = "ableToSprint")
    private boolean ableToSprint = true;

    private Data(OfflinePlayer player) {
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

    // May return null.
    public OfflinePlayer getPlayer() {
        return this.player;
    }

    public final Profile getProfile() {
        return RpgPlugin.getInstance().getProfile(this.player);
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

    public float getStamina() {
        return this.stamina;
    }

    public float getStaminaCapacity() {
        return this.staminaCapacity;
    }

    public void incrementStamina(float amount) {
        this.stamina += stamina;
    }

    public void decrementStamina(float amount) {
        this.stamina -= stamina;
    }

    public void setStaminaCapacity(float staminaCapacity) {
        this.staminaCapacity = staminaCapacity;
    }

    public float getBodyTemperature() {
        return this.bodyTemperature;
    }

    public void setBodyTemperature(float bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public void setInfected(boolean infected) {
        this.infected = infected;
    }

    public boolean isInfected() {
        return this.infected;
    }

    public void setAbleToSprint(boolean ableToSprint) {
        this.ableToSprint = ableToSprint;
    }

    public boolean isAbleToSprint() {
        return this.ableToSprint;
    }
}
