/*
 *  mcRPG is a open source rpg bukkit/spigot plugin.
 *  Copyright (C) 2015 Matej Kormuth 
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
package eu.matejkormuth.rpgdavid.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.bukkit.Blocks;
import eu.matejkormuth.bukkit.inventory.ItemStackBuilder;
import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.inventorymenu.InventoryMenu;
import eu.matejkormuth.rpgdavid.inventorymenu.InventoryMenuItem;
import eu.matejkormuth.rpgdavid.money.Currencies;
import eu.matejkormuth.rpgdavid.money.Money;

public class BanksListener implements Listener {
    private static final String MONEY_BANK_LINE = "[Bank]";
    private static final String ITEM_BANK_LINE = "[ItemBank]";

    @EventHandler
    private void onSignClick(final PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (Blocks.isSign(event.getClickedBlock())) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                if (sign.getLine(0).equalsIgnoreCase(MONEY_BANK_LINE)) {
                    this.openBank(event);
                } else if (sign.getLine(0).equalsIgnoreCase(ITEM_BANK_LINE)) {
                    this.openItemBank(event);
                }
            }
        }
    }

    private void openItemBank(PlayerInteractEvent event) {
        event.getPlayer().openInventory(event.getPlayer().getEnderChest());
    }

    private void openBank(PlayerInteractEvent event) {
        List<InventoryMenuItem> items = new ArrayList<>();

        // Normal add.
        items.add(new InventoryMenuItem(named(RpgPlugin.t("t_normal_store"),
                Material.GOLD_BLOCK),
                new eu.matejkormuth.rpgdavid.inventorymenu.Action() {
                    @Override
                    public void execute(Player player) {
                        try {
                            int amount = RpgPlugin.getInstance()
                                    .getProfile(player).getNormalMoney()
                                    .getAmount();
                            RpgPlugin
                                    .getInstance()
                                    .getProfile(player)
                                    .getNormalMoney()
                                    .subtract(
                                            new Money(amount, Currencies.NORMAL));
                            RpgPlugin.getInstance().getMoneyBank()
                                    .getAccount(player).getNormal()
                                    .add(new Money(amount, Currencies.NORMAL));
                            player.sendMessage(ChatColor.GREEN
                                    + "Peniaze ulozene do banky.");
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + e.getMessage());
                        }
                    }
                }, 0, false));
        // Normal take.
        items.add(new InventoryMenuItem(named(RpgPlugin.t("t_normal_take"),
                Material.GOLD_BLOCK),
                new eu.matejkormuth.rpgdavid.inventorymenu.Action() {
                    @Override
                    public void execute(Player player) {
                        try {
                            int amount = RpgPlugin.getInstance().getMoneyBank()
                                    .getAccount(player).getNormal().getAmount();
                            RpgPlugin
                                    .getInstance()
                                    .getMoneyBank()
                                    .getAccount(player)
                                    .getNormal()
                                    .subtract(
                                            new Money(amount, Currencies.NORMAL));
                            RpgPlugin.getInstance().getProfile(player)
                                    .getNormalMoney()
                                    .add(new Money(amount, Currencies.NORMAL));
                            player.sendMessage(ChatColor.GREEN
                                    + "Peniaze vybrate z banky.");
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + e.getMessage());
                        }
                    }
                }, 1, false));

        // Premium add.
        items.add(new InventoryMenuItem(named(RpgPlugin.t("t_premium_store"),
                Material.IRON_BLOCK),
                new eu.matejkormuth.rpgdavid.inventorymenu.Action() {
                    @Override
                    public void execute(Player player) {
                        try {
                            int amount = RpgPlugin.getInstance()
                                    .getProfile(player).getPremiumMoney()
                                    .getAmount();
                            RpgPlugin
                                    .getInstance()
                                    .getProfile(player)
                                    .getPremiumMoney()
                                    .subtract(
                                            new Money(amount,
                                                    Currencies.PREMIUM));
                            RpgPlugin.getInstance().getMoneyBank()
                                    .getAccount(player).getPremium()
                                    .add(new Money(amount, Currencies.PREMIUM));
                            player.sendMessage(ChatColor.GREEN
                                    + "Peniaze ulozene do banky.");
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + e.getMessage());
                        }
                    }
                }, 1, false));
        // Premium take.
        items.add(new InventoryMenuItem(named(RpgPlugin.t("t_premium_take"),
                Material.IRON_BLOCK),
                new eu.matejkormuth.rpgdavid.inventorymenu.Action() {
                    @Override
                    public void execute(Player player) {
                        try {
                            int amount = RpgPlugin.getInstance().getMoneyBank()
                                    .getAccount(player).getPremium()
                                    .getAmount();
                            RpgPlugin
                                    .getInstance()
                                    .getMoneyBank()
                                    .getAccount(player)
                                    .getPremium()
                                    .subtract(
                                            new Money(amount,
                                                    Currencies.PREMIUM));
                            RpgPlugin.getInstance().getProfile(player)
                                    .getPremiumMoney()
                                    .add(new Money(amount, Currencies.PREMIUM));
                            player.sendMessage(ChatColor.GREEN
                                    + "Peniaze vybrate z banky.");
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + e.getMessage());
                        }
                    }
                }, 2, false));

        // Create and open inventory menu.
        InventoryMenu im = new InventoryMenu(9, "Banka", items);
        im.showTo(event.getPlayer());
    }

    public static final ItemStack named(String name, Material material) {
        return new ItemStackBuilder(material).name(name).build();
    }
}
