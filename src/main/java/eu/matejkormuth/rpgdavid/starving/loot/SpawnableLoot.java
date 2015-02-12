package eu.matejkormuth.rpgdavid.starving.loot;

import org.bukkit.Location;

public interface SpawnableLoot {
    void spawn(Location location, Loot loot);
}
