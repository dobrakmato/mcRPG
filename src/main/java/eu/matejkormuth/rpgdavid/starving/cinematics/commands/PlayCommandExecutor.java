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
package eu.matejkormuth.rpgdavid.starving.cinematics.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.cinematics.Cinematics;
import eu.matejkormuth.rpgdavid.starving.cinematics.Clip;
import eu.matejkormuth.rpgdavid.starving.cinematics.ClipPlayer;
import eu.matejkormuth.rpgdavid.starving.cinematics.ClipPlayer.ClipPlayerListener;

public class PlayCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length > 0) {
                String name = args[0];
                boolean silent = false;
                if (args.length > 1 && args[1].equalsIgnoreCase("-silent")) {
                    silent = true;
                }

                try {
                    if (!silent) {
                        sender.sendMessage(ChatColor.YELLOW
                                + "Using engine: "
                                + Cinematics.getCinematics().getImplementationName());
                    }

                    // Load cinematic.
                    if (!silent) {
                        sender.sendMessage(ChatColor.YELLOW + "Loading file "
                                + name);
                    }
                    Clip clip = Cinematics.getCinematics().loadClip(name);

                    // Play cinematic.
                    if (!silent) {
                        sender.sendMessage(ChatColor.YELLOW
                                + "Playing for player " + sender.getName());
                    }
                    ClipPlayer player = Cinematics.getCinematics().createPlayer(
                            clip);
                    player.getCamera().addObserver((Player) sender);
                    player.addListener(new ClipPlayerListener() {
                        @Override
                        public void onStop(ClipPlayer clipPlayer) {
                            sender.sendMessage(ChatColor.YELLOW
                                    + "Stopped!");
                        }
                    });
                    player.play();
                } catch (RuntimeException e) {
                    sender.sendMessage(ChatColor.RED + "Play command failed: "
                            + e.toString());
                }

            } else {
                sender.sendMessage(ChatColor.RED
                        + "Usage: /play <name> [-silent]");
            }
            return true;
        } else {
            sender.sendMessage(ChatColor.RED
                    + "This command can be only used by players.");
        }
        return false;
    }

}
