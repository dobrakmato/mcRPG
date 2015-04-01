/*
 *  Starving is a open source bukkit/spigot mmo game.
 *  Copyright (C) 2014-2015 Matej Kormuth
 *  This file is a part of Starving. <http://www.starving.eu>
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
package eu.matejkormuth.rpgdavid.starving.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.Data;

public class RpCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                String type = args[0];
                if (type.equalsIgnoreCase("players")) {
                    Data.of((Player) sender).setResourcePack("players");
                    sender.sendMessage(ChatColor.GREEN
                            + "Resource pack type set to 'players'!");
                } else if (type.equalsIgnoreCase("builders")) {
                    Data.of((Player) sender).setResourcePack("builders");
                    sender.sendMessage(ChatColor.GREEN
                            + "Resource pack type set to 'builders'!");
                } else {
                    sender.sendMessage(ChatColor.RED
                            + "Invalid resource pack type!");
                }
            } else {
                sender.sendMessage(ChatColor.RED
                        + "Usage: /rp <players/builders>");
            }
        }
        return true;
    }

}
