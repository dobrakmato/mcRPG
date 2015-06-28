package eu.matejkormuth.rpgdavid.starving.scripts;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.starving.items.base.Item;
import eu.matejkormuth.rpgdavid.starving.npc.types.HumanNPC;
import eu.matejkormuth.rpgdavid.starving.quests.Mission;

public interface IScriptAPI {

    /* CONTEXT METHODS */
    Player currentPlayer();

    Mission currentQuest();

    /* NULL CHECK METHOD */
    boolean exists(Object obj);

    /* ITEMSTACK FACTORY METHODS */

    ItemStack itemStack(int material, int amount);

    ItemStack itemStack(int material, int amount, byte data);

    ItemStack itemStack(int material, int amount, byte data, short durability);

    ItemStack itemStack(Material material, int amount);

    ItemStack itemStack(Material material, int amount, byte data);

    ItemStack itemStack(Material material, int amount, byte data,
            short durability);

    /* CUSTOM ITEMSTACK FACTORY METHODS */

    ItemStack customItemStack(String type, int amount);

    ItemStack customItemStack(Class<? extends Item> type, int amount);

    /* WORLD FACTORY METHODS */

    World world(String name);

    World worldOf(Entity entity);

    World worldOf(int entityId);

    /* LOCATION FACTORY METHODS */

    Location loc(int x, int y, int z);

    Location loc(int x, int y, int z, float yaw, float pitch);

    Location loc(World w, int x, int y, int z);

    Location loc(World w, int x, int y, int z, float yaw, float pitch);

    /* TELEPORT METHODS */

    void teleport(Entity e, Location loc);

    void teleport(Entity e, Entity entity);

    void teleport(Entity e, String warp);

    void teleport(int entityId, Location loc);

    void teleport(int entityId, int entityToId);

    void teleport(int entityId, String warp);

    /* DAMAGE METHOD */
    void damage(LivingEntity livingEntity, double damage);

    void damage(int livingEntityId, double damage);

    /* SET AND GET ITEM IN HAND */

    void iih(LivingEntity livingEntity, ItemStack itemStack);

    void iih(int livingEntityId, ItemStack itemStack);

    ItemStack iih(LivingEntity livingEntity);

    ItemStack iig(int livingEntityId);

    /* ADD TAKE AND CHECK INVENTORY METHODS */

    void invGive(InventoryHolder invHolder, ItemStack itemStack);

    void invTake(InventoryHolder invHolder, ItemStack itemStack);

    void invCheck(InventoryHolder invHolder, ItemStack itemStack);

    /* ZOMBIE RELATED METHODS */
    Zombie zombie(Location loc);

    /* NPC RELATED METHODS */
    HumanNPC playerNpc(String skin, String name);

    void addTrait(String traitName);

    /* PLAYER STORAGE RELATED METHODS */
    <T> T storageGet(String key);

    <T> T storageGet(Player player, String key);

    void storageSet(String key, String value);

    void storageSet(Player player, String key, String value);

    /* MISSION RELATED METHODS */
    Mission mission(String id);
    
    /* PLAYER FLAG METHODS */
    void addFlag(Player player, String string);

    void removeFlag(Player player, String string);

    void checkFlag(Player player, String string);

    /* POTION EFFECT METHODS */
    void addPotion(LivingEntity livingEntity, String potion, int level,
            int length);

    void addPotion(int livingEntityId, String potion, int level, int length);

    void removePotion(LivingEntity livingEntity, String potion);

    void removePotion(int livingEntityId, String potion);

    /* ITEM DROPS METHODS */
    void drop(ItemStack itemStack, Location location);

    /* SOUND METHODS */
    void playSound(Player player, String soundName);

    void playSound(Player player, String soundName, float volume);

    void playSound(Player player, String soundName, float volume, float pitch);

    void playSound(Location location, String soundName, float volume,
            float pitch);
}
