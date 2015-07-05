package eu.matejkormuth.rpgdavid.starving.events;

import org.bukkit.event.Event;

public abstract class TimeEvent extends Event {
    private final long totalTicks;

    public TimeEvent(long totalTicks) {
        this.totalTicks = totalTicks;
    }

    public long getTotalTicks() {
        return totalTicks;
    }
}
