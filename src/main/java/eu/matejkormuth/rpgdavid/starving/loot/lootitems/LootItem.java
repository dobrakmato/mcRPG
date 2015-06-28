package eu.matejkormuth.rpgdavid.starving.loot.lootitems;

import org.bukkit.inventory.ItemStack;

/**
 * Represents item that is spawned as a loot. You can get ItemStack by calling
 * ItemStack. The returned item stack needen't to be the same each time method
 * is called.
 */
public interface LootItem {
    
    /**
     * Returns ItemStack provided by implementation of this interface. It can be
     * constant or it can change by each call.
     * 
     * @return item stack that should be spawned
     */
    ItemStack getItemStack();
}
