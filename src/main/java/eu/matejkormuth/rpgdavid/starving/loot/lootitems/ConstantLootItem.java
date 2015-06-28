package eu.matejkormuth.rpgdavid.starving.loot.lootitems;

import org.bukkit.inventory.ItemStack;

/**
 * Represents one constant item stack as LootItem.
 */
public class ConstantLootItem implements LootItem {

    private final ItemStack is;

    public ConstantLootItem(ItemStack is) {
        this.is = is;
    }

    @Override
    public ItemStack getItemStack() {
        return is;
    }

}
