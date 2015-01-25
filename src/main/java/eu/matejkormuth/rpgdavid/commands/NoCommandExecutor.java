package eu.matejkormuth.rpgdavid.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.RpgPlugin;

public class NoCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;

        // If has offered quest.
        if (RpgPlugin.getInstance().getQuestManager().hasOffer(player)) {
            RpgPlugin.getInstance().getQuestManager().removeOffer(player);
            sender.sendMessage(ChatColor.RED + "You declined quest!");
        } else {
            sender.sendMessage(ChatColor.RED
                    + "You don't have any offered quest!");
        }
        return true;
    }
}
