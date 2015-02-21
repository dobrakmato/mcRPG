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
import eu.matejkormuth.rpgdavid.money.Currency;
import eu.matejkormuth.rpgdavid.money.Money;
import eu.matejkormuth.rpgdavid.money.Money.MoneyException;

public class BanksListener implements Listener {
    private static final String MONEY_BANK_LINE = "[Bank]";
    private static final String ITEM_BANK_LINE = "[ItemBank]";

    private eu.matejkormuth.rpgdavid.inventorymenu.Action normalStore = new MoneyBankStoreMoneyAction(
            50, Currencies.NORMAL);
    private eu.matejkormuth.rpgdavid.inventorymenu.Action premiumStore = new MoneyBankStoreMoneyAction(
            50, Currencies.PREMIUM);
    private eu.matejkormuth.rpgdavid.inventorymenu.Action normalTake = new MoneyBankTakeMoneyAction(
            50, Currencies.NORMAL);
    private eu.matejkormuth.rpgdavid.inventorymenu.Action premiumTake = new MoneyBankTakeMoneyAction(
            50, Currencies.PREMIUM);

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
                Material.GOLD_BLOCK), this.normalStore, 0, false));
        // Normal take.
        items.add(new InventoryMenuItem(named(RpgPlugin.t("t_normal_take"),
                Material.GOLD_BLOCK), this.normalTake, 1, false));

        // Premium add.
        items.add(new InventoryMenuItem(named(RpgPlugin.t("t_premium_store"),
                Material.IRON_BLOCK), this.premiumStore, 2, false));
        // Premium take.
        items.add(new InventoryMenuItem(named(RpgPlugin.t("t_premium_take"),
                Material.IRON_BLOCK), this.premiumTake, 3, false));

        // Create and open inventory menu.
        InventoryMenu im = new InventoryMenu(9, "Banka", items);
        im.showTo(event.getPlayer());
    }

    public static final ItemStack named(String name, Material material) {
        return new ItemStackBuilder(material).name(name).build();
    }

    public static final class MoneyBankStoreMoneyAction implements
            eu.matejkormuth.rpgdavid.inventorymenu.Action {
        private int amount;
        private Currency curr;

        public MoneyBankStoreMoneyAction(int amount, Currency curr) {
            this.amount = amount;
            this.curr = curr;
        }

        @Override
        public void execute(Player player) {
            Money playerMoney;
            Money playerBank;
            if (curr == Currencies.NORMAL) {
                playerMoney = RpgPlugin.getInstance().getProfile(player)
                        .getNormalMoney();
                playerBank = RpgPlugin.getInstance().getMoneyBank()
                        .getAccount(player).getNormal();
            } else {
                playerMoney = RpgPlugin.getInstance().getProfile(player)
                        .getPremiumMoney();
                playerBank = RpgPlugin.getInstance().getMoneyBank()
                        .getAccount(player).getPremium();
            }
            // Transaction.
            Money transaction = new Money(this.amount, this.curr);
            try {
                playerMoney.subtractSafe(transaction);
                playerBank.add(transaction);
                player.sendMessage(ChatColor.GREEN + "Transakce uspesna.");
            } catch (MoneyException e) {
                player.sendMessage(ChatColor.RED + e.getMessage());
            }
        }
    }

    public static final class MoneyBankTakeMoneyAction implements
            eu.matejkormuth.rpgdavid.inventorymenu.Action {
        private int amount;
        private Currency curr;

        public MoneyBankTakeMoneyAction(int amount, Currency curr) {
            this.amount = amount;
            this.curr = curr;
        }

        @Override
        public void execute(Player player) {
            Money playerMoney;
            Money playerBank;
            if (curr == Currencies.NORMAL) {
                playerMoney = RpgPlugin.getInstance().getProfile(player)
                        .getNormalMoney();
                playerBank = RpgPlugin.getInstance().getMoneyBank()
                        .getAccount(player).getNormal();
            } else {
                playerMoney = RpgPlugin.getInstance().getProfile(player)
                        .getPremiumMoney();
                playerBank = RpgPlugin.getInstance().getMoneyBank()
                        .getAccount(player).getPremium();
            }
            // Transaction.
            Money transaction = new Money(this.amount, this.curr);
            try {
                playerBank.subtractSafe(transaction);
                playerMoney.add(transaction);
                player.sendMessage(ChatColor.GREEN + "Transakce uspesna.");
            } catch (MoneyException e) {
                player.sendMessage(ChatColor.RED + e.getMessage());
            }
        }

    }
}
