/*
 *  Starving is a open source bukkit/spigot mmo game.
 *  Copyright (C) 2014-2015 Matej Kormuth
 *  This file is a part of Starving. <http://www.starving.eu>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package eu.matejkormuth.rpgdavid.starving.items;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.chemistry.ChemicalCompound;
import eu.matejkormuth.rpgdavid.starving.items.base.BlockWithData;
import eu.matejkormuth.rpgdavid.starving.items.base.ChemicalItem;
import eu.matejkormuth.rpgdavid.starving.items.base.ConsumableItem;
import eu.matejkormuth.rpgdavid.starving.items.base.Craftable;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;
import eu.matejkormuth.rpgdavid.starving.items.base.MeleeWeapon;
import eu.matejkormuth.rpgdavid.starving.items.blocks.Log2D12;
import eu.matejkormuth.rpgdavid.starving.items.blocks.Log2D13;
import eu.matejkormuth.rpgdavid.starving.items.blocks.LogD12;
import eu.matejkormuth.rpgdavid.starving.items.blocks.LogD13;
import eu.matejkormuth.rpgdavid.starving.items.blocks.LogD14;
import eu.matejkormuth.rpgdavid.starving.items.blocks.LogD15;
import eu.matejkormuth.rpgdavid.starving.items.blocks.oakstairs.OakStairsD1;
import eu.matejkormuth.rpgdavid.starving.items.blocks.oakstairs.OakStairsD2;
import eu.matejkormuth.rpgdavid.starving.items.blocks.oakstairs.OakStairsD3;
import eu.matejkormuth.rpgdavid.starving.items.blocks.oakstairs.OakStairsD4;
import eu.matejkormuth.rpgdavid.starving.items.blocks.oakstairs.OakStairsD5;
import eu.matejkormuth.rpgdavid.starving.items.blocks.oakstairs.OakStairsD6;
import eu.matejkormuth.rpgdavid.starving.items.blocks.oakstairs.OakStairsD7;
import eu.matejkormuth.rpgdavid.starving.items.blocks.oakstairs.OakStairsD8;
import eu.matejkormuth.rpgdavid.starving.items.clothing.Boots;
import eu.matejkormuth.rpgdavid.starving.items.clothing.BulletproofVest;
import eu.matejkormuth.rpgdavid.starving.items.clothing.CamoflageHelmet;
import eu.matejkormuth.rpgdavid.starving.items.clothing.CamoflageThickPants;
import eu.matejkormuth.rpgdavid.starving.items.clothing.CamoflageThickShirt;
import eu.matejkormuth.rpgdavid.starving.items.clothing.Cap;
import eu.matejkormuth.rpgdavid.starving.items.clothing.Hat;
import eu.matejkormuth.rpgdavid.starving.items.clothing.Jeans;
import eu.matejkormuth.rpgdavid.starving.items.clothing.RemVest;
import eu.matejkormuth.rpgdavid.starving.items.clothing.RubberShoes;
import eu.matejkormuth.rpgdavid.starving.items.clothing.Sandals;
import eu.matejkormuth.rpgdavid.starving.items.clothing.Shield;
import eu.matejkormuth.rpgdavid.starving.items.clothing.Shirt;
import eu.matejkormuth.rpgdavid.starving.items.clothing.Shoes;
import eu.matejkormuth.rpgdavid.starving.items.clothing.Shorts;
import eu.matejkormuth.rpgdavid.starving.items.clothing.TShirt;
import eu.matejkormuth.rpgdavid.starving.items.clothing.ThickPants;
import eu.matejkormuth.rpgdavid.starving.items.clothing.ThickShoes;
import eu.matejkormuth.rpgdavid.starving.items.clothing.Windbreaker;
import eu.matejkormuth.rpgdavid.starving.items.clothing.WinterPants;
import eu.matejkormuth.rpgdavid.starving.items.consumables.MagicMushroom;
import eu.matejkormuth.rpgdavid.starving.items.drinks.Fanta;
import eu.matejkormuth.rpgdavid.starving.items.drinks.RedBull;
import eu.matejkormuth.rpgdavid.starving.items.drinks.Sprite;
import eu.matejkormuth.rpgdavid.starving.items.explosives.C4;
import eu.matejkormuth.rpgdavid.starving.items.explosives.Detonator;
import eu.matejkormuth.rpgdavid.starving.items.explosives.FlareGun;
import eu.matejkormuth.rpgdavid.starving.items.explosives.Grenade;
import eu.matejkormuth.rpgdavid.starving.items.explosives.Molotov;
import eu.matejkormuth.rpgdavid.starving.items.explosives.Petard;
import eu.matejkormuth.rpgdavid.starving.items.explosives.RPG7;
import eu.matejkormuth.rpgdavid.starving.items.explosives.SmokeShell;
import eu.matejkormuth.rpgdavid.starving.items.firearms.AK47;
import eu.matejkormuth.rpgdavid.starving.items.firearms.Revolver;
import eu.matejkormuth.rpgdavid.starving.items.firearms.Dragunov;
import eu.matejkormuth.rpgdavid.starving.items.firearms.Glock;
import eu.matejkormuth.rpgdavid.starving.items.firearms.M16;
import eu.matejkormuth.rpgdavid.starving.items.firearms.MP5;
import eu.matejkormuth.rpgdavid.starving.items.firearms.Mossberg500;
import eu.matejkormuth.rpgdavid.starving.items.firearms.NickyAnaconda;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedAK47;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedRevolver;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedDragunov;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedGlock;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedM16;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedMP5;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedMossberg500;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedNickyAnaconda;
import eu.matejkormuth.rpgdavid.starving.items.food.CannedMeat;
import eu.matejkormuth.rpgdavid.starving.items.food.CannedVegetables;
import eu.matejkormuth.rpgdavid.starving.items.food.Raspberry;
import eu.matejkormuth.rpgdavid.starving.items.food.Strawberry;
import eu.matejkormuth.rpgdavid.starving.items.itemmeta.deprecated.ChemicalItemMetaWrapper;
import eu.matejkormuth.rpgdavid.starving.items.medical.Bandage;
import eu.matejkormuth.rpgdavid.starving.items.medical.Patch;
import eu.matejkormuth.rpgdavid.starving.items.medical.Splint;
import eu.matejkormuth.rpgdavid.starving.items.melee.Axe;
import eu.matejkormuth.rpgdavid.starving.items.melee.IronPipe;
import eu.matejkormuth.rpgdavid.starving.items.melee.IronPipeWithMetaRodsAndKnife;
import eu.matejkormuth.rpgdavid.starving.items.melee.IronPipeWithMetalRods;
import eu.matejkormuth.rpgdavid.starving.items.melee.Knife;
import eu.matejkormuth.rpgdavid.starving.items.melee.Machete;
import eu.matejkormuth.rpgdavid.starving.items.melee.WoodenStick;
import eu.matejkormuth.rpgdavid.starving.items.melee.WoodenStickWithMetalRods;
import eu.matejkormuth.rpgdavid.starving.items.melee.WoodenStickWithMetalRodsAndKnife;
import eu.matejkormuth.rpgdavid.starving.items.misc.DisinfectionTablets;
import eu.matejkormuth.rpgdavid.starving.items.misc.Flashlight;
import eu.matejkormuth.rpgdavid.starving.items.misc.GalvanicCell;
import eu.matejkormuth.rpgdavid.starving.items.misc.Parachute;
import eu.matejkormuth.rpgdavid.starving.items.misc.Toolset;
import eu.matejkormuth.rpgdavid.starving.items.misc.Transmitter;
import eu.matejkormuth.rpgdavid.starving.items.ranged.Crossbow;
import eu.matejkormuth.rpgdavid.starving.items.ranged.LoadedCrossbow;

public class ItemManager implements Listener {
    private Set<Item> items;

    private EnumMap<Rarity, List<Item>> rarityMapping;
    private EnumMap<Category, List<Item>> categoryMapping;

    public ItemManager() {
        Starving.getInstance().getLogger().info("Initializing ItemManager...");
        this.items = new HashSet<Item>();
        // Register all items.
        this.registerAll();
        this.registerAdditionalRecipes();
        // Register events.
        Bukkit.getPluginManager().registerEvents(this,
                Starving.getInstance().getPlugin());
        Starving.getInstance()
                .getLogger()
                .info("We have " + this.items.size()
                        + " different registered items!");
    }

    private void registerAdditionalRecipes() {
        // TODO: Register chemical crafting recipes.
        // TODO: Register water crafting recipes.
    }

    private void registerAll() {
        this.register(new Parachute());
        this.register(new GalvanicCell());
        this.register(new Transmitter());
        this.register(new Toolset());
        this.register(new MagicMushroom());
        this.register(new DisinfectionTablets());
        this.register(new Flashlight());

        this.registerFood();
        this.registerFirearms();
        this.registerRanged();
        this.registerMelee();
        this.registerMedical();
        this.registerDrinks();
        this.registerClothing();
        this.registerBlocks();
        this.registerExplosives();
    }

    private void registerExplosives() {
        this.register(new C4());
        this.register(new Detonator());
        this.register(new FlareGun());
        this.register(new Grenade());
        this.register(new Molotov());
        this.register(new Petard());
        this.register(new RPG7());
        this.register(new SmokeShell());
    }

    private void registerMelee() {
        this.register(new IronPipe());
        this.register(new IronPipeWithMetalRods());
        this.register(new IronPipeWithMetaRodsAndKnife());
        this.register(new Knife());
        this.register(new WoodenStick());
        this.register(new WoodenStickWithMetalRods());
        this.register(new WoodenStickWithMetalRodsAndKnife());
        this.register(new Axe());
        this.register(new Machete());
    }

    private void registerFood() {
        this.register(new CannedMeat());
        this.register(new CannedVegetables());
        this.register(new Raspberry());
        this.register(new Strawberry());
    }

    private void registerBlocks() {
        this.registerBlocksWithData();
    }

    private void registerBlocksWithData() {
        this.register(new LogD12());
        this.register(new LogD13());
        this.register(new LogD14());
        this.register(new LogD15());

        this.register(new Log2D12());
        this.register(new Log2D13());

        this.register(new OakStairsD1());
        this.register(new OakStairsD2());
        this.register(new OakStairsD3());
        this.register(new OakStairsD4());
        this.register(new OakStairsD5());
        this.register(new OakStairsD6());
        this.register(new OakStairsD7());
        this.register(new OakStairsD8());
    }

    private void registerRanged() {
        this.register(new Crossbow());
        this.register(new LoadedCrossbow());
    }

    private void registerFirearms() {
        // Unscoped variations.
        this.register(new AK47());
        this.register(new Glock());
        this.register(new Dragunov());
        this.register(new M16());
        this.register(new Mossberg500());
        this.register(new MP5());
        this.register(new Revolver());
        this.register(new NickyAnaconda());

        // Scoped variations.
        this.register(new ScopedAK47());
        this.register(new ScopedGlock());
        this.register(new ScopedDragunov());
        this.register(new ScopedM16());
        this.register(new ScopedMossberg500());
        this.register(new ScopedMP5());
        this.register(new ScopedRevolver());
        this.register(new ScopedNickyAnaconda());
    }

    private void registerDrinks() {
        this.register(new Fanta());
        this.register(new Sprite());
        this.register(new RedBull());
    }

    private void registerClothing() {
        this.register(new Boots());
        this.register(new BulletproofVest());
        this.register(new CamoflageHelmet());
        this.register(new CamoflageThickPants());
        this.register(new CamoflageThickShirt());
        this.register(new Cap());
        this.register(new Hat());
        this.register(new Jeans());
        this.register(new RemVest());
        this.register(new RubberShoes());
        this.register(new Sandals());
        this.register(new Shield());
        this.register(new Shirt());
        this.register(new Shoes());
        this.register(new Shorts());
        this.register(new ThickPants());
        this.register(new ThickShoes());
        this.register(new TShirt());
        this.register(new Windbreaker());
        this.register(new WinterPants());
    }

    private void registerMedical() {
        this.register(new Bandage());
        this.register(new Patch());
        this.register(new Splint());
    }

    private void register(final Item item) {
        if (this.items.contains(item)) {
            // Do not register more then once.
            return;
        }
        // If is item craftable, register recipe.
        if (item instanceof Craftable) {
            Bukkit.addRecipe(((Craftable) item).getRecipe());
        }
        // Add to set.
        this.items.add(item);
    }

    public ItemStack newItemStack(Class<? extends Item> clazz) {
        return this.newItemStack(clazz, 1);
    }

    public ItemStack newItemStack(Class<? extends Item> clazz, int amount) {
        for (Item i : this.items) {
            if (clazz.isInstance(i)) {
                return i.toItemStack(amount);
            }
        }
        return null;
    }

    public Item findItem(final ItemStack itemStack) {
        // Special case for chemicals.
        if (this.isChemical(itemStack)) {
            // TODO: Try to look at known chemical compounds.
            return new ParsedChemicalItem(new ChemicalItemMetaWrapper(
                    itemStack.getItemMeta()).get());
        }

        // For every other item.
        for (Item item : this.items) {
            if (item.matches(itemStack)) {
                return item;
            }
        }
        return null;
    }

    public boolean isItemOf(final ItemStack itemStack,
            Class<? extends Item> type) {
        Item i = this.findItem(itemStack);
        if (i == null) {
            return false;
        }
        // TODO: This might have bad performance.
        return i.getClass().equals(type);
    }

    private boolean isChemical(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }

        if (itemStack.getType() != Material.POTION) {
            return false;
        }
        if (itemStack.hasItemMeta()) {
            List<String> l = itemStack.getItemMeta().getLore();
            return l.size() > 1 && l.get(0).contains("chemical");
        }
        return false;
    }

    public List<Item> getItems() {
        return new ArrayList<>(this.items);
    }

    @EventHandler
    private void onInteract(final PlayerInteractEvent event) {
        Item item = this.findItem(event.getItem());

        if (item instanceof BlockWithData) {
            return;
        }

        if (item != null) {
            InteractResult result = item.onInteract(event.getPlayer(),
                    event.getAction(), event.getClickedBlock(),
                    event.getBlockFace());

            if (result.getUsedAmount() > event.getItem().getAmount()) {
                throw new IllegalArgumentException(
                        "Used amount is bigger then itemStack amount!");
            }

            // Simulate use.
            if (result.isUsed()) {
                // If use all.
                if (result.getUsedAmount() == -1) {
                    event.getItem().setAmount(0);
                } else {
                    event.getItem().setAmount(
                            event.getItem().getAmount()
                                    - result.getUsedAmount());
                }
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onInteractWith(final PlayerInteractEntityEvent event) {
        Item item = this.findItem(event.getPlayer().getItemInHand());

        if (item instanceof BlockWithData) {
            return;
        }

        if (item != null) {
            item.onInteractWith(event.getPlayer(), event.getRightClicked());
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onConsume(final PlayerItemConsumeEvent event) {
        Item item = this.findItem(event.getItem());
        if (item != null) {
            if (item instanceof ConsumableItem) {
                ((ConsumableItem) item).onConsume(event.getPlayer());
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onBlockPlace(final BlockPlaceEvent event) {
        Item item = this.findItem(event.getItemInHand());
        if (item != null) {
            if (item instanceof BlockWithData) {
                ((BlockWithData) item).onPlaced(event.getPlayer(),
                        event.getBlockPlaced());
            }
        }
    }

    @EventHandler
    private void onAttack(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (((Player) event.getDamager()).getItemInHand() != null) {
                Item item = this.findItem(((Player) event.getDamager())
                        .getItemInHand());
                if (item != null) {
                    if (item instanceof MeleeWeapon) {
                        ((MeleeWeapon) item).onAttack(
                                (Player) event.getDamager(),
                                (LivingEntity) event.getEntity(),
                                event.getDamage());
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    // Crafting and max. stack amount emulation.

    @EventHandler
    private void onItemStackMerge(final InventoryClickEvent event) {
        // Max stack emulation.
        if (event.getCursor() != null) {
            // Beware: event.getCurrentItem() returns undocumented null, when
            // throwing items out of inventory.
            if (event.getCurrentItem() == null) {
                // We have nothing to merge.
                return;
            }

            // If merging stacks of same type.
            if (event.getCurrentItem().getType()
                    .equals(event.getCursor().getType())) {
                Item item1 = this.findItem(event.getCurrentItem());
                Item item2 = this.findItem(event.getCursor());
                // Check if both are custom.
                if (item1 != null && item2 != null) {
                    int totalAmount = event.getCurrentItem().getAmount()
                            + event.getCursor().getAmount();
                    // Check if this merge exceeds maxStackAmount.
                    if (item1.getMaxStackAmount() > totalAmount) {
                        // Disable this merge.
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    /**
     * Helper class to allow easy initialization of {@link ChemicalItem} through
     * {@link ChemicalItemMetaWrapper}.
     */
    private static class ParsedChemicalItem extends ChemicalItem {
        public ParsedChemicalItem(ChemicalCompound chemicalCompound) {
            super("ParsedChemicalItem", chemicalCompound);
        }

        @Override
        public Recipe getRecipe() {
            // This class does not provide recipe.
            return null;
        }

        @Override
        protected void onConsume0(Player player) {
        }
    }
}
