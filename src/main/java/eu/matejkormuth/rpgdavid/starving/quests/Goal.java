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

    public boolean isCompleted(MissionContext ctx) {
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
    public abstract boolean checkConditions(MissionContext ctx);

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
