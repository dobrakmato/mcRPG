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

public abstract class Goal {
    private final Mission parentMission;
    private final String id;
    private final List<Goal> requiredGoals;

    public Goal(Mission parent, String id, List<Goal> requiredGoals) {
        this.parentMission = parent;
        this.id = id;
        this.requiredGoals = requiredGoals;
    }

    public boolean isCompleted(MissionPlayerContext ctx) {
        // Query first mission storage.
        if (ctx.isCompleted(this)) {
            return true;
        }

        // Check
        boolean completed = this.checkConditions(ctx);
        // If it is completed, save that to mission storage.
        if (completed) {
            ctx.setCompleted(this, completed);
        }

        return completed;
    }

    /**
     * Should check if conditions for goal completion are met.
     * 
     * @param ctx
     *            context to provide access to mission storage
     * @return whether the conditions are met
     */
    public abstract boolean checkConditions(MissionPlayerContext ctx);

    public Mission getParentMission() {
        return parentMission;
    }

    public List<Goal> getPreviousGoals() {
        return requiredGoals;
    }

    public String getId() {
        return id;
    }
}
