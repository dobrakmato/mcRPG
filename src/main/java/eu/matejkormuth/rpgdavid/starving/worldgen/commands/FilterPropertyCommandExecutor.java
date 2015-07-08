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
import eu.matejkormuth.rpgdavid.starving.worldgen.filters.base.Filter;
import eu.matejkormuth.rpgdavid.starving.worldgen.filters.base.FilterProperties;
import eu.matejkormuth.rpgdavid.starving.worldgen.filters.base.FilterProperty;

public class FilterPropertyCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.isOp() || sender.hasPermission("wg")) {
                if (args.length == 2) {
                    Filter f = Starving.getInstance().getWorldGenManager().getSession(
                            (Player) sender).getFilter();
                    String property = args[0];
                    String value = args[1];

                    if (f.isPropertySupported(property)) {
                        FilterProperties playerfprops = Starving.getInstance().getWorldGenManager().getSession(
                                (Player) sender).getFilterProperties();
                        if (value.equalsIgnoreCase("true")
                                || value.equalsIgnoreCase("false")) {
                            playerfprops.set(new FilterProperty(property,
                                    Boolean.valueOf(value)));
                            sender.sendMessage(ChatColor.GREEN + "Property '"
                                    + property
                                    + "' set to boolean (float) value '"
                                    + value + "'.");
                        } else {
                            playerfprops.set(new FilterProperty(property,
                                    Float.parseFloat(value)));
                            sender.sendMessage(ChatColor.GREEN + "Property '"
                                    + property + "' set to float value '"
                                    + value + "'.");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Current filter '"
                                + f.getName()
                                + "' does not supports property '" + property
                                + "'!");
                    }
                } else {
                    sender.sendMessage(ChatColor.YELLOW
                            + "=== Current filter properties ===");
                    FilterProperties fps = Starving.getInstance().getWorldGenManager().getSession(
                            (Player) sender).getFilterProperties();
                    for (FilterProperty fp : fps.getProperties().values()) {
                        sender.sendMessage(ChatColor.GOLD + " " + fp.getType()
                                + " " + ChatColor.RED + fp.getName()
                                + ": " + ChatColor.GREEN + fp.asFloat());
                    }
                    sender.sendMessage(ChatColor.YELLOW
                            + "=================================");

                    sender.sendMessage(ChatColor.RED
                            + "Usage: /fp <property> <value>");
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
