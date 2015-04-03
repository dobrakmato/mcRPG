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
package eu.matejkormuth.rpgdavid.starving.worldgen.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.worldgen.brushes.BrushType;

public class BrushTypeCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.isOp() || sender.hasPermission("wg")) {
                if (args.length == 1) {
                    String type = args[0];
                    if (type.equalsIgnoreCase("square")) {
                        Starving.getInstance().getWorldGenManager().getSession(
                                (Player) sender).setBrushType(BrushType.SQUARE);
                        sender.sendMessage(ChatColor.GREEN
                                + "Brush type set to SQUARE.");
                    } else if (type.equalsIgnoreCase("circle")) {
                        Starving.getInstance().getWorldGenManager().getSession(
                                (Player) sender).setBrushType(BrushType.CIRCLE);
                        sender.sendMessage(ChatColor.GREEN
                                + "Brush type set to CIRCLE.");
                    } else {
                        sender.sendMessage(ChatColor.RED
                                + "Unsupported brush type!");
                    }
                } else {
                    sender.sendMessage(ChatColor.YELLOW
                            + "Current brush type: "
                            + Starving.getInstance().getWorldGenManager().getSession(
                                    (Player) sender).getBrush().getType());
                    sender.sendMessage(ChatColor.RED
                            + "Usage: /bt <circle/square>");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Not enough permissions!");
            }
        } else {
            sender.sendMessage(ChatColor.RED
                    + "This reason can be only used by players!");
        }
        return true;
    }

}
