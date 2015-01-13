package eu.matejkormuth.rpgdavid.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerHeadCommandExecutor implements CommandExecutor {

	public boolean onCommand(CommandSender sender,
			Command command, String label,
			String[] args) {
		if(command.getName().equals("playerhead")) {
			if(sender.hasPermission("playerhead")) {
				if(args.length == 1) {
					if(args[0].isEmpty()) {
						sender.sendMessage(ChatColor.RED + "Name can't be empty!");
					}
					
					// Fallback to vanilla / essentials command.
					((Player) sender).performCommand("give " + sender.getName() + " minecraft:skull 1 3 {SkullOwner:" + args[0] + "}");
					
				} else {
					sender.sendMessage(ChatColor.RED + "Invalid usage! Usage: /playerhead <name>");
				}
				return true;
			}
		} 
		return false;
	}

}
