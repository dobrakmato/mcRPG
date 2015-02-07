package eu.matejkormuth.rpgdavid.starving.tasks;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class TimeUpdater extends RepeatingTask {
    private final List<World> worlds;
    private long fullTime;

    public TimeUpdater() {
        // Cache worlds for better performance and memory usage.
        this.worlds = Bukkit.getWorlds();
        this.fullTime = this.worlds.get(0).getFullTime();
    }

    @Override
    public void run() {
        // Increment time.
        this.fullTime++;
        this.setTime();
    }

    private void setTime() {
        for (World w : this.worlds) {
            w.setFullTime(this.fullTime);
        }
    }
}
