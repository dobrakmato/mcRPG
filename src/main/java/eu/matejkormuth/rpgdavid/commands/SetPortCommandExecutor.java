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

import eu.matejkormuth.rpgdavid.RpgPlugin;

public class SetPortCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (sender.isOp()) {
            if (sender instanceof Player) {
                if (args.length == 1) {
                    String name = args[0];
                    RpgPlugin.getInstance().setPortLocation(name,
                            ((Player) sender).getLocation());
                    sender.sendMessage(ChatColor.GREEN + "Success!");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "/setport <name>");
                    return true;
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Not enough permissions!");
        }
        return false;
    }
}
