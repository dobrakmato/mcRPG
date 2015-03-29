package eu.matejkormuth.rpgdavid.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.money.Currencies;
import eu.matejkormuth.rpgdavid.money.Money;

public class MoneyCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (sender.isOp()) {
            if (args.length == 4) {
                String playerName = args[1];
                String currency = args[3];

                Player p = null;
                if ((p = Bukkit.getPlayer(playerName)) == null) {
                    sender.sendMessage(ChatColor.RED + "Can't find player "
                            + playerName + "!");
                    return false;
                }

                int amount = Integer.parseInt(args[2]);
                switch (args[0]) {
                    case "add":
                        RpgPlugin.getInstance().getProfile(p).getNormalMoney()
                                .add(new Money(amount, Currencies.NORMAL));
                        if (currency.equalsIgnoreCase(Currencies.NORMAL
                                .getAbbr())
                                || currency.equalsIgnoreCase(Currencies.NORMAL
                                        .getName())) {
                            RpgPlugin.getInstance().getProfile(p)
                                    .getNormalMoney()
                                    .add(new Money(amount, Currencies.NORMAL));
                        } else if (currency.equalsIgnoreCase(Currencies.PREMIUM
                                .getAbbr())
                                || currency.equalsIgnoreCase(Currencies.PREMIUM
                                        .getName())) {
                            RpgPlugin.getInstance().getProfile(p)
                                    .getPremiumMoney()
                                    .add(new Money(amount, Currencies.PREMIUM));
                        } else {
                            sender.sendMessage(ChatColor.RED + "Currency "
                                    + currency + " is not valid currency!");
                            return false;
                        }
                        sender.sendMessage(ChatColor.GREEN + "Added " + amount
                                + " to " + playerName);
                        break;
                    case "set":
                        if (currency.equalsIgnoreCase(Currencies.NORMAL
                                .getAbbr())
                                || currency.equalsIgnoreCase(Currencies.NORMAL
                                        .getName())) {
                            RpgPlugin.getInstance().getProfile(p)
                                    .getNormalMoney()
                                    .setAmount(amount);
                        } else if (currency.equalsIgnoreCase(Currencies.PREMIUM
                                .getAbbr())
                                || currency.equalsIgnoreCase(Currencies.PREMIUM
                                        .getName())) {
                            RpgPlugin.getInstance().getProfile(p)
                                    .getPremiumMoney()
                                    .setAmount(amount);
                        } else {
                            sender.sendMessage(ChatColor.RED + "Currency "
                                    + currency + " is not valid currency!");
                            return false;
                        }
                        sender.sendMessage(ChatColor.GREEN + "Money set!");
                        break;
                    default:
                        sender.sendMessage(ChatColor.RED
                                + "Usage: /money <add/set> <playerName> <amount> <currency>");
                        break;
                }
            } else {
                sender.sendMessage(ChatColor.RED
                        + "Usage: /money <add/set> <playerName> <amount> <currency>");
            }
        } else {
            sender.sendMessage(ChatColor.RED
                    + "You don't have enough presmissions!");
        }
        return true;
    }
}
