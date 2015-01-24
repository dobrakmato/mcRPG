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
package eu.matejkormuth.rpgdavid;

import java.io.Serializable;
import java.util.UUID;

public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;

    // Main properties.
    private UUID uuid;
    private Character character;
    private long xp = 0L;
    private int mana = 600;
    private int maxMana = 900;

    // Currencies
    private int florins = 0;
    private int dollars = 100;

    // Character properties.
    private long vampire_lastBitten;
    private int magican_currentSpell;

    // Quest properties.
    private String currentQuestId;

    public Profile() {
    }

    protected Profile(UUID uuid, Character character) {
        this.uuid = uuid;
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public void setUniqueId(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean hasCharacter() {
        return this.character != null;
    }

    public long getXp() {
        return xp;
    }

    public void setXp(long xp) {
        this.xp = xp;
    }

    public void giveXp(final int amount) {
        this.xp += amount;
    }

    public void setCurrentQuestId(final String currentQuestId) {
        this.currentQuestId = currentQuestId;
    }

    public String getCurrentQuestId() {
        return currentQuestId;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
    }

    public void giveMana(final int amount) {
        if (this.mana + amount < this.maxMana) {
            this.mana += amount;
        } else {
            this.mana = this.maxMana;
        }
    }

    public boolean takeMana(final int amount) {
        if (this.mana >= amount) {
            this.mana -= amount;
            return true;
        }
        return false;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getFlorins() {
        return florins;
    }

    public void setFlorins(int amount) {
        this.florins = amount;
    }

    public void addFlorins(int amount) {
        this.florins += amount;
    }

    public boolean takeFlorins(int amount) {
        if (this.florins >= amount) {
            this.florins = amount;
            return true;
        }
        return false;
    }

    public int getDollars() {
        return dollars;
    }

    public void setDollars(int amount) {
        this.dollars = amount;
    }

    public void addDollars(int amount) {
        this.dollars += amount;
    }

    public boolean takeDollars(int amount) {
        if (this.dollars >= amount) {
            this.dollars = amount;
            return true;
        }
        return false;
    }

    // -------------------- VAMPIRE METHODS

    public void setLastBittenNow() {
        this.vampire_lastBitten = System.currentTimeMillis();
    }

    public boolean canBite() {
        return System.currentTimeMillis() > this.vampire_lastBitten + 1000 * 60;
    }

    public long getLastBitten() {
        return this.vampire_lastBitten;
    }

    // --- MAGICAN METHODS

    public void setMagican_currentSpell(int magican_currentSpell) {
        this.magican_currentSpell = magican_currentSpell;
    }

    public int getMagican_currentSpell() {
        return magican_currentSpell;
    }
}
