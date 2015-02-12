package eu.matejkormuth.rpgdavid.starving.loot;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import eu.matejkormuth.rpgdavid.bukkitfixes.FlagMetadataValue;

public class ArmorStandSpawnableLoot implements SpawnableLoot {
    public static final String METADATA_KEY = "ArmorStandSpawnableLoot";

    @Override
    public void spawn(Location location, Loot loot) {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(
                location, EntityType.ARMOR_STAND);
        armorStand.setItemInHand(loot.getItemStack());
        armorStand.setMetadata(METADATA_KEY, new FlagMetadataValue());
    }
}
