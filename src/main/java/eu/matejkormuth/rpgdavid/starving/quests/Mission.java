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

    public boolean canStart(MissionContext ctx) {
        for (Mission m : requiredCompleted) {
            if (!m.isCompleted(ctx)) {
                return false;
            }
        }
        return true;
    }

    public boolean isCompleted(MissionContext ctx) {
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
