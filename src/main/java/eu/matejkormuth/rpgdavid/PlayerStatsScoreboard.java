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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import eu.matejkormuth.rpgdavid.money.Currencies;

public class PlayerStatsScoreboard {
    private Scoreboard scoreboard;
    private Objective objective;
    private Profile profile;
    private Player player;

    private Score xpScore;
    private Score manaScore;
    private Score currency1Score;
    private Score currency2Score;

    public PlayerStatsScoreboard(final Player player) {
        this.player = player;
        this.profile = RpgPlugin.getInstance().getProfile(player);
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        this.objective = this.scoreboard.registerNewObjective("sidebar_main",
                "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.objective.setDisplayName(this.profile.getCharacter().getName());

        this.xpScore = this.objective.getScore("XP");
        this.manaScore = this.objective.getScore("Mana");
        this.currency1Score = this.objective.getScore(Currencies.NORMAL
                .getName());
        this.currency2Score = this.objective.getScore(Currencies.PREMIUM
                .getName());
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public Player getPlayer() {
        return player;
    }

    public void update() {
        this.xpScore.setScore((int) this.profile.getXp());
        this.manaScore.setScore(this.profile.getMana());
        this.currency1Score.setScore(this.profile.getNormalMoney().getAmount());
        this.currency2Score
                .setScore(this.profile.getPremiumMoney().getAmount());
    }
}
