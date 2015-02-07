package eu.matejkormuth.rpgdavid.starving.tasks;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class TimeUpdater extends RepeatingTask {
    private final List<World> worlds;
    private long time;

    public TimeUpdater() {
        // Cache worlds for better performance and memory usage.
        this.worlds = Bukkit.getWorlds();
    }

    @Override
    public void run() {
        if (this.time == 23999L) {
            this.time = 0L;
        }
        this.setTime();
    }

    private void setTime() {
        for (World w : this.worlds) {
            w.setTime(this.time);
        }
    }
}
