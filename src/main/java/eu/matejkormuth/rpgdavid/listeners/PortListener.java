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

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import eu.matejkormuth.bukkit.Blocks;
import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.money.Currencies;
import eu.matejkormuth.rpgdavid.money.Currency;
import eu.matejkormuth.rpgdavid.money.Money;
import eu.matejkormuth.rpgdavid.money.Money.MoneyException;

public class PortListener implements Listener {
    private static final String SIGNIFICANT_LINE = "[Port]";

    @EventHandler
    private void onInteract(final PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (Blocks.isSign(event.getClickedBlock())) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                if (sign.getLine(0).equalsIgnoreCase(SIGNIFICANT_LINE)) {
                    // Thanks to bukkit, this will throw exception for invalid
                    // signs...
                    String target = sign.getLine(1);
                    String price = sign.getLine(2);

                    Location loc = RpgPlugin.getInstance().getPortLocation(
                            target);
                    if (loc != null) {
                        // Parse money.
                        String[] parts = price.split(" ");
                        int amount = Integer.valueOf(parts[0]);
                        String currency = parts[1];
                        Currency curr = Currencies.NORMAL;
                        if (Currencies.PREMIUM.getAbbr().startsWith(currency)) {
                            curr = Currencies.PREMIUM;
                        }

                        // Transaction.
                        Money transaction = new Money(amount, curr);
                        try {
                            if (curr == Currencies.NORMAL) {
                                RpgPlugin.getInstance()
                                        .getProfile(event.getPlayer())
                                        .getNormalMoney()
                                        .subtractSafe(transaction);
                            } else {
                                RpgPlugin.getInstance()
                                        .getProfile(event.getPlayer())
                                        .getPremiumMoney()
                                        .subtractSafe(transaction);
                            }
                            event.getPlayer().teleport(loc);
                            event.getPlayer().sendMessage(
                                    ChatColor.GREEN
                                            + RpgPlugin.t("t_teleportsuccess"));
                        } catch (MoneyException e) {
                            event.getPlayer()
                                    .sendMessage(
                                            ChatColor.RED
                                                    + RpgPlugin
                                                            .t("t_not_enough_money"));
                        }

                    } else {
                        event.getPlayer()
                                .sendMessage(
                                        ChatColor.RED
                                                + RpgPlugin
                                                        .t("t_port_not_found"));
                    }
                }
            }
        }
    }
}
