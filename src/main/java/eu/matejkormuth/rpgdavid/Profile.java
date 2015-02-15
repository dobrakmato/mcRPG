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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import eu.matejkormuth.rpgdavid.money.Currencies;
import eu.matejkormuth.rpgdavid.money.Money;

public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;

    // Main properties.
    private UUID uuid;
    private Character character;
    private long xp = 10L;
    private int mana = 600;
    private int maxMana = 900;

    // Currencies
    private Money normalMoney = new Money(100, Currencies.NORMAL);
    private Money premiumMoney = new Money(10, Currencies.PREMIUM);

    // Character properties.
    private int magican_currentSpell;

    // Quest properties.
    private List<String> activeQuests;
    private Map<String, Boolean> quests;
    public Profile() {
        this.quests = new HashMap<String, Boolean>();
    }

    protected Profile(UUID uuid, Character character) {
        this.uuid = uuid;
        this.character = character;
        this.quests = new HashMap<String, Boolean>();
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

    public List<String> getActiveQuestIds() {
        return this.activeQuests;
    }

    public void setActiveQuestIds(List<String> activeQuests) {
        this.activeQuests = activeQuests;
    }

    public boolean isQuestActive(String questId) {
        return this.activeQuests.contains(questId);
    }

    public boolean addActiveQuest(String questId) {
        if (this.activeQuests.contains(questId)) {
            return false;
        }
        this.activeQuests.add(questId);
        return true;
    }

    public boolean removeActiveQuest(String questId) {
        return this.activeQuests.remove(questId);
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

    public Money getNormalMoney() {
        return normalMoney;
    }

    public Money getPremiumMoney() {
        return premiumMoney;
    }

    public void setPremiumMoney(Money premiumMoney) {
        this.premiumMoney = premiumMoney;
    }

    public void setNormalMoney(Money normalMoney) {
        this.normalMoney = normalMoney;
    }

    public void setQuestCompleted(String questId, boolean completed) {
        this.quests.put(questId, completed);
    }

    public boolean isQuestCompleted(String questId) {
        if (this.quests.containsKey(questId)) {
            return this.quests.get(questId).booleanValue();
        }
        return false;
    }

    public Map<String, Boolean> getCompletedQuests() {
        return this.quests;
    }

    public void setCompletedQuests(Map<String, Boolean> completedQuests) {
        this.quests = completedQuests;
    }

    // --- MAGICAN METHODS

    public void setMagican_currentSpell(int magican_currentSpell) {
        this.magican_currentSpell = magican_currentSpell;
    }

    public int getMagican_currentSpell() {
        return magican_currentSpell;
    }
}
