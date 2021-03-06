/*
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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.Data;

public class RpCommandExecutor implements CommandExecutor {

    private static final String DEFAULT_RP_RESOULTION = "_128";
    
    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length > 0) {
                String type = args[0];
                
                String res = DEFAULT_RP_RESOULTION;
                if(args.length == 2) {
                    res = "_" + Integer.valueOf(args[1]);
                }
                
                // TODO: Add resolutions.
                
                if (type.equalsIgnoreCase("players")) {
                    Data.of((Player) sender).setResourcePack("players");
                    ((Player) sender).setResourcePack("http://www.starving.eu/2/rp/latest.zip");
                    sender.sendMessage(ChatColor.GREEN
                            + "Resource pack type set to 'players'!");
                } else if (type.equalsIgnoreCase("builders")) {
                    Data.of((Player) sender).setResourcePack("builders");
                    ((Player) sender).setResourcePack("http://www.starving.eu/2/rp/latest_builder.zip");
                    sender.sendMessage(ChatColor.GREEN
                            + "Resource pack type set to 'builders'!");
                } else if (type.equalsIgnoreCase("empty")) {
                    Data.of((Player) sender).setResourcePack("empty");
                    ((Player) sender).setResourcePack("http://www.starving.eu/2/rp/empty.zip");
                    sender.sendMessage(ChatColor.GREEN
                            + "Resource pack type set to 'empty'!");
                } else {
                    sender.sendMessage(ChatColor.RED
                            + "Invalid resource pack type!");
                }
            } else {
                sender.sendMessage(ChatColor.RED
                        + "Usage: /rp <players/builders/empty>");
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1) {
                String c = args[0];
                if (c.equalsIgnoreCase("reloadall")) {
                    // Reload resource pack for all online players.
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (Data.of(p).getResourcePack().equals("builders")) {
                            p.setResourcePack("http://www.starving.eu/2/rp/latest_builder.zip");
                        } else if (Data.of(p).getResourcePack().equals(
                                "players")) {
                            p.setResourcePack("http://www.starving.eu/2/rp/latest.zip");
                        } else {
                            p.setResourcePack("http://www.starving.eu/2/rp/empty.zip");
                        }
                    }
                } else {
                    sender.sendMessage("Unsupported command!");
                }
            } else {
                sender.sendMessage("Invalid command!");
            }
        } else {

        }
        return true;
    }
}
