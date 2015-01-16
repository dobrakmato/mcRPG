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
