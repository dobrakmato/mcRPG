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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.chemistry.ChemicalCompound;
import eu.matejkormuth.rpgdavid.starving.items.base.ChemicalItem;
import eu.matejkormuth.rpgdavid.starving.items.base.ConsumableItem;
import eu.matejkormuth.rpgdavid.starving.items.base.Craftable;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;
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
import eu.matejkormuth.rpgdavid.starving.items.firearms.AK47;
import eu.matejkormuth.rpgdavid.starving.items.firearms.ColtAnaconda;
import eu.matejkormuth.rpgdavid.starving.items.firearms.DesertEagle;
import eu.matejkormuth.rpgdavid.starving.items.firearms.Dragunov;
import eu.matejkormuth.rpgdavid.starving.items.firearms.M16;
import eu.matejkormuth.rpgdavid.starving.items.firearms.Mossberg500;
import eu.matejkormuth.rpgdavid.starving.items.firearms.NickyAnaconda;
import eu.matejkormuth.rpgdavid.starving.items.firearms.SilencedDesertEagle;
import eu.matejkormuth.rpgdavid.starving.items.itemmeta.ChemicalItemMetaWrapper;
import eu.matejkormuth.rpgdavid.starving.items.medical.Bandage;
import eu.matejkormuth.rpgdavid.starving.items.medical.Patch;
import eu.matejkormuth.rpgdavid.starving.items.medical.Splint;
import eu.matejkormuth.rpgdavid.starving.items.misc.DisinfectionTablets;
import eu.matejkormuth.rpgdavid.starving.items.misc.GalvanicCell;
import eu.matejkormuth.rpgdavid.starving.items.misc.Parachute;
import eu.matejkormuth.rpgdavid.starving.items.misc.Toolset;
import eu.matejkormuth.rpgdavid.starving.items.misc.Transmitter;

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
        // Regsiter events.
        Bukkit.getPluginManager().registerEvents(this,
                Starving.getInstance().getPlugin());
        Starving.getInstance()
                .getLogger()
                .info("We have " + this.items.size()
                        + " different registered items!");
    }

    private void registerAdditionalRecipes() {
        // TODO: Register chemical crafting recipies.
        // TODO: Register water crafting recipies.
    }

    private void registerAll() {
        this.register(new Parachute());
        this.register(new GalvanicCell());
        this.register(new Transmitter());
        this.register(new Toolset());
        this.register(new MagicMushroom());
        this.register(new DisinfectionTablets());

        this.registerFirearms();
        this.registerMedical();
        this.registerDrinks();
        this.registerClothing();
    }

    private void registerFirearms() {
        this.register(new AK47());
        this.register(new DesertEagle());
        this.register(new Dragunov());
        this.register(new M16());
        this.register(new Mossberg500());
        this.register(new SilencedDesertEagle());
        this.register(new ColtAnaconda());
        this.register(new NickyAnaconda());
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
