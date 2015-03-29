package eu.matejkormuth.rpgdavid.starving.npc.interfaces;

import org.bukkit.Location;

import eu.matejkormuth.rpgdavid.starving.npc.path.Path;

public interface PathFollower {
    void walkTo(Location location);

    void walkBy(Path path);

    void stopWalking();
}
