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
package eu.matejkormuth.rpgdavid.starving.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class OrderedScoreboard {
    // Dummy criteria for no criteria in Minecraft.
    private static final String DUMMY_CRITERIA = "dummy";

    private Scoreboard rawScoreboard;
    private Objective table;
    private Score[] scores;

    public OrderedScoreboard(int lines) {
        // this.lines = new String[lines];
        this.scores = new Score[lines];
        this.rawScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.createObjective();
    }

    public void setTitle(String title) {
        this.table.setDisplayName(title);
    }

    public void setLine(int line, String content) {
        if (line < 0) {
            throw new IllegalArgumentException("line is less then 0");
        }

        if (line >= this.scores.length) {
            throw new IllegalArgumentException(
                    "line is more then scoreboard length");
        }

        this.replaceScoreAt(line, content);
    }

    public Scoreboard getScoreboard() {
        return this.rawScoreboard;
    }

    private void replaceScoreAt(int line, String content) {
        // Obtain old information.
        String oldEntry = this.scores[line].getEntry();
        int position = this.scores[line].getScore();
        // Remove old score.
        this.scores[line] = null;
        this.rawScoreboard.resetScores(oldEntry);
        // Create new score at specified postion.
        this.scores[line] = this.table.getScore(content);
        this.scores[line].setScore(position);
    }

    private void createObjective() {
        this.table = this.rawScoreboard.registerNewObjective(
                "osb_" + this.hashCode(), DUMMY_CRITERIA);
        this.table.setDisplaySlot(DisplaySlot.SIDEBAR);
    }
}
