package eu.matejkormuth.rpgdavid;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.inventoryutils.ItemStackBuilder;

public class HunterArrowGivingUpdater implements Runnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Profile p = RpgPlugin.getInstance().getProfile(player);
            if (p != null) {
                // Give hunter one arrow.
                if (p.getCharacter() == Characters.HUNTER) {
                    player.getInventory().addItem(
                            new ItemStackBuilder(Material.ARROW).build());
                }
            }
        }
    }
}
