package eu.matejkormuth.rpgdavid.starving.npc.interfaces;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface LivingNPC {
    void lookAt(Location location);

    void lookAt(Entity entity);

    void setRotation(float pitch, float yaw);
}
