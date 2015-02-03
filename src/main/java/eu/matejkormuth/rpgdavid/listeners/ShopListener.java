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

import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.bukkit.Blocks;
import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.money.Currencies;
import eu.matejkormuth.rpgdavid.money.Money;
import eu.matejkormuth.rpgdavid.money.Money.MoneyException;

public class ShopListener implements Listener {
    private final static String SHOP_LINE = "[Buy]";

    @EventHandler
    private void onSignClick(final PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (Blocks.isSign(event.getClickedBlock())) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                if (sign.getLine(0).equalsIgnoreCase(SHOP_LINE)) {
                    String customItemId = sign.getLine(1);
                    String amount = sign.getLine(2);
                    String priceString = sign.getLine(3);

                    // Check for valid price.
                    String[] parts = priceString.split(Pattern.quote(" "));
                    int price = Integer.valueOf(parts[0]);
                    String currency = parts[1];

                    if (RpgPlugin.getInstance().hasCustomItem(customItemId)) {
                        if (amount != null) {
                            // Pay with NORMAL currency.
                            if (currency.startsWith(Currencies.NORMAL.getName()
                                    .substring(0, 1))) {
                                try {
                                    RpgPlugin
                                            .getInstance()
                                            .getProfile(event.getPlayer())
                                            .getNormalMoney()
                                            .subtractSafe(
                                                    new Money(price,
                                                            Currencies.NORMAL));

                                    ItemStack items = RpgPlugin.getInstance()
                                            .getCustomItem(customItemId,
                                                    Integer.valueOf(amount));
                                    event.getPlayer().getInventory()
                                            .addItem(items);
                                } catch (MoneyException ex) {
                                    event.getPlayer()
                                            .sendMessage(
                                                    ChatColor.RED
                                                            + "You don't have enough money!");
                                }
                            }
                            // Pay with premium currency.
                            else if (currency.startsWith(Currencies.PREMIUM
                                    .getName().substring(0, 1))) {
                                try {
                                    RpgPlugin
                                            .getInstance()
                                            .getProfile(event.getPlayer())
                                            .getPremiumMoney()
                                            .subtractSafe(
                                                    new Money(price,
                                                            Currencies.PREMIUM));

                                    ItemStack items = RpgPlugin.getInstance()
                                            .getCustomItem(customItemId,
                                                    Integer.valueOf(amount));
                                    event.getPlayer().getInventory()
                                            .addItem(items);
                                } catch (MoneyException ex) {
                                    event.getPlayer()
                                            .sendMessage(
                                                    ChatColor.RED
                                                            + "You don't have enough money!");
                                }
                            }
                            // Sign has invalid currency specified.
                            else {
                                event.getPlayer()
                                        .sendMessage(
                                                ChatColor.RED
                                                        + "Invalid currency found on 4th line!");
                            }

                            event.getPlayer().sendMessage(
                                    ChatColor.GREEN + "You brought " + amount
                                            + " of " + customItemId + "!");
                        } else {
                            event.getPlayer()
                                    .sendMessage(
                                            ChatColor.RED
                                                    + "Item amount must be specified on 3th line.");
                        }
                    } else {
                        event.getPlayer().sendMessage(
                                ChatColor.RED + "Item '" + customItemId
                                        + "' is not properly configured!");
                    }
                }
            }
        }
    }
}
