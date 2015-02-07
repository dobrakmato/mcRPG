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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.items.base.ConsumableItem;
import eu.matejkormuth.rpgdavid.starving.items.base.Craftable;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;
import eu.matejkormuth.rpgdavid.starving.items.consumables.MagicMushroom;
import eu.matejkormuth.rpgdavid.starving.items.misc.GalvanicCell;
import eu.matejkormuth.rpgdavid.starving.items.misc.Parachute;
import eu.matejkormuth.rpgdavid.starving.items.misc.Toolset;
import eu.matejkormuth.rpgdavid.starving.items.misc.Transmitter;

public class ItemManager implements Listener {
    private Set<Item> items;

    public ItemManager() {
        this.items = new HashSet<Item>();
        // Register all items.
        this.registerAll();
        this.registerAdditionalRecipes();
        // Regsiter events.
        Bukkit.getPluginManager().registerEvents(this,
                Starving.getInstance().getPlugin());
    }

    private void registerAdditionalRecipes() {

    }

    private void registerAll() {
        this.register(new Parachute());
        this.register(new GalvanicCell());
        this.register(new Transmitter());
        this.register(new Toolset());
        this.register(new MagicMushroom());
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

    public Item getItem(final ItemStack itemStack) {
        for (Item item : this.items) {
            if (item.matches(itemStack)) {
                return item;
            }
        }
        return null;
    }

    public List<Item> getItems() {
        return new ArrayList<>(this.items);
    }

    @EventHandler
    private void onInteract(final PlayerInteractEvent event) {
        Item item = this.getItem(event.getItem());
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
        Item item = this.getItem(event.getPlayer().getItemInHand());
        if (item != null) {
            item.onInteractWith(event.getPlayer(), event.getRightClicked());
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onConsume(final PlayerItemConsumeEvent event) {
        Item item = this.getItem(event.getItem());
        if (item != null) {
            if (item instanceof ConsumableItem) {
                ((ConsumableItem) item).onConsume(event.getPlayer());
                event.setCancelled(true);
            }
        }
    }
}
