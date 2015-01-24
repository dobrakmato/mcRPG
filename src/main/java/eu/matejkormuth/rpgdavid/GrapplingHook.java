package eu.matejkormuth.rpgdavid;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GrapplingHook extends ItemStack {
    public static final double MAX_USE_DISTANCE = 6.0D;
    
    public GrapplingHook() {
        super(Material.CARROT_STICK);
    }
}
