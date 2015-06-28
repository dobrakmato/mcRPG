package eu.matejkormuth.rpgdavid.starving.loot.lootitems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.inventory.ItemStack;

/**
 * Combines more LootItem-s to one. Calling will choose one random child
 * LootItem to provide item stack.
 */
public class CombinedLootItem implements LootItem {

    private static final Random random = new Random();
    private List<LootItem> lootItems;

    public CombinedLootItem() {
        this.lootItems = new ArrayList<LootItem>();
    }

    public int size() {
        return lootItems.size();
    }

    public Iterator<LootItem> iterator() {
        return lootItems.iterator();
    }

    public boolean add(LootItem e) {
        return lootItems.add(e);
    }

    public boolean remove(Object o) {
        return lootItems.remove(o);
    }

    public boolean addAll(Collection<? extends LootItem> c) {
        return lootItems.addAll(c);
    }

    public void clear() {
        lootItems.clear();
    }

    @Override
    public ItemStack getItemStack() {
        return lootItems.get(random.nextInt(lootItems.size())).getItemStack();
    }

}
