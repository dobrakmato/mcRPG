package eu.matejkormuth.rpgdavid.starving.items;

import org.bukkit.inventory.ItemStack;

public abstract class Item {
    protected ItemStack itemStack;

    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
