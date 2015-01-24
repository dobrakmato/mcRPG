package eu.matejkormuth.rpgdavid;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Dagger extends ItemStack {
    private static final String DAGGER_NAME = ChatColor.RESET + "Dýka";

    public Dagger() {
        super(Material.STONE_SWORD);

        ItemMeta im = this.getItemMeta();
        im.setDisplayName(DAGGER_NAME);
        im.setLore(Arrays.asList(ChatColor.RED + "Damage: 12 HP"));
        this.setItemMeta(im);
    }

    public static boolean isDagger(ItemStack itemInHand) {
        return itemInHand.getType().equals(Material.STONE_SWORD)
                && itemInHand.hasItemMeta()
                && itemInHand.getItemMeta().getDisplayName()
                        .equals(DAGGER_NAME);
    }
}
