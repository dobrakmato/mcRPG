package eu.matejkormuth.rpgdavid.starving.npc.interfaces;

import org.bukkit.entity.Entity;

public interface EntityFollower {
    void follow(Entity e);

    void stopFollowing();
}
