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
package eu.matejkormuth.rpgdavid.starving.quests;

import java.util.List;

public abstract class Mission {
    private final String id;
    private final String name;
    private final String description;

    private final List<Mission> requiredCompleted;
    private final List<Goal> missionGoals;
    private final List<Reward> missionRewards;

    public Mission(String id, String name, String description,
            List<Mission> requiredCompleted, List<Goal> missionGoals,
            List<Reward> missionRewards) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requiredCompleted = requiredCompleted;
        this.missionGoals = missionGoals;
        this.missionRewards = missionRewards;
    }

    public boolean canStart(MissionPlayerContext ctx) {
        for (Mission m : requiredCompleted) {
            if (!m.isCompleted(ctx)) {
                return false;
            }
        }
        return true;
    }

    public boolean isCompleted(MissionPlayerContext ctx) {
        // First check storage.
        if (ctx.isCompleted(this)) {
            return true;
        }

        // Check mission goals.
        for (Goal g : missionGoals) {
            if (!g.isCompleted(ctx)) {
                return false;
            }
        }

        // Mission is completed, save it.
        ctx.setCompleted(this, true);
        return true;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Goal> getMissionGoals() {
        return missionGoals;
    }

    public List<Reward> getMissionRewards() {
        return missionRewards;
    }

    public List<Mission> getRequiredCompleted() {
        return requiredCompleted;
    }
}
