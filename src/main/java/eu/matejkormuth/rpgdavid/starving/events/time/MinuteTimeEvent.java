package eu.matejkormuth.rpgdavid.starving.events.time;

import org.bukkit.event.HandlerList;

import eu.matejkormuth.rpgdavid.starving.events.TimeEvent;

public class MinuteTimeEvent extends TimeEvent {

    private static final HandlerList handlers = new HandlerList();
    
    public MinuteTimeEvent(long l) {
        super(l);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
