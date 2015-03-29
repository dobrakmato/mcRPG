package eu.matejkormuth.rpgdavid.starving.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpeedCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                float speed = Float.parseFloat(args[0]);
                ((Player) sender).setWalkSpeed(speed);
                ((Player) sender).setFlySpeed(speed);
            } else {
                sender.sendMessage(ChatColor.RED + "/setspeed <speed>");
            }
        }
        return true;
    }
}
