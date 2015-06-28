package eu.matejkormuth.rpgdavid.starving.loot.spawns;

import eu.matejkormuth.rpgdavid.starving.loot.lootitems.LootItem;

/**
 * Represents method and function of loot spawning.
 */
public interface LootSpawn {
    
    /**
     * Spawns specified loot.
     * 
     * @param item
     *            loot to spawn
     */
    void spawn(LootItem item);
}
