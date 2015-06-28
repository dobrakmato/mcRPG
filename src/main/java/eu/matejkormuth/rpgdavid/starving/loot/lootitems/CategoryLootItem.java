package eu.matejkormuth.rpgdavid.starving.loot.lootitems;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.items.Category;
import eu.matejkormuth.rpgdavid.starving.items.ItemManager;
import eu.matejkormuth.rpgdavid.starving.items.Rarity;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;

/**
 * Represents random loot item that has at least category or rarity specified.
 * Returned item stack it random.
 */
public class CategoryLootItem implements LootItem {

    private static final Random random = new Random();
    private final Category category;
    private final Rarity rarity;

    public CategoryLootItem(Category category) {
        this(category, null);
    }

    public CategoryLootItem(Rarity rarity) {
        this(null, rarity);
    }

    public CategoryLootItem(Category category, Rarity rarity) {
        this.category = category;
        this.rarity = rarity;
    }

    @Override
    public ItemStack getItemStack() {
        if (category == null) {
            return findRandomByRarity();
        }

        if (rarity == null) {
            return findRandomByCategory();
        }

        return findRandomByCategoryAndRarity();
    }

    private ItemStack findRandomByCategoryAndRarity() {
        return find(this.category, this.rarity);
    }

    private ItemStack findRandomByCategory() {
        Rarity randomRarity = randomRarity();
        return find(this.category, randomRarity);
    }

    private Rarity randomRarity() {
        return Rarity.values()[random.nextInt(Rarity.values().length)];
    }

    private ItemStack findRandomByRarity() {
        Category randomCategory = randomCategory();
        return find(randomCategory, this.rarity);
    }

    private Category randomCategory() {
        return Category.values()[random.nextInt(Category.values().length)];
    }

    private ItemStack find(Category category, Rarity rarity) {
        ItemManager im = Starving.getInstance().getItemManager();
        List<Item> itemsMeetingCriteria = im.getItems()
                .stream()
                .filter(item -> item.getCategory() == category)
                .filter(item -> item.getRarity() == rarity)
                .collect(Collectors.toCollection(ArrayList::new));

        return itemsMeetingCriteria.get(
                random.nextInt(itemsMeetingCriteria.size())).toItemStack();
    }

}
