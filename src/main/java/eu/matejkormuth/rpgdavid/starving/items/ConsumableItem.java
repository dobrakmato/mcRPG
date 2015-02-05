package eu.matejkormuth.rpgdavid.starving.items;

import org.bukkit.entity.Player;

public abstract class ConsumableItem extends Item {
    public abstract void onConsume(final Player player);
}
