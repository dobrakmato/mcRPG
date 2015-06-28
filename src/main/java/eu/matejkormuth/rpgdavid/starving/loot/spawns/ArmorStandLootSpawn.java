package eu.matejkormuth.rpgdavid.starving.loot.spawns;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import eu.matejkormuth.rpgdavid.bukkitfixes.FlagMetadataValue;
import eu.matejkormuth.rpgdavid.starving.loot.lootitems.LootItem;

/**
 * Represents loot spawned by armor stand at specified location.
 */
public class ArmorStandLootSpawn implements LootSpawn {

    public static final String METADATA_KEY = "ArmorStandSpawnableLoot";
    
    private final Location location;

    public ArmorStandLootSpawn(Location location) {
        this.location = location;
    }

    @Override
    public void spawn(LootItem item) {
        ArmorStand armorStand = createArmorStand();

        armorStand.setItemInHand(item.getItemStack());
        armorStand.setMetadata(METADATA_KEY, new FlagMetadataValue());
    }

    private ArmorStand createArmorStand() {
        return (ArmorStand) location.getWorld().spawnEntity(location,
                EntityType.ARMOR_STAND);
    }

}
