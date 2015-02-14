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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.party.Party;

public class PartyCommandExecutor implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (command.getName().equals("party")) {
            if (args.length > 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED
                            + "This command can be only executed as player!");
                    return true;
                }
                Player p = (Player) sender;

                String subcommand = args[0];
                switch (subcommand) {
                case "create":
                    if (Party.getParty(p) == null) {
                        // create party
                        new Party(p);
                    } else {
                        p.sendMessage(ChatColor.RED
                                + RpgPlugin.t("t_alreadyinparty"));
                    }
                    break;
                case "invite":
                    if (Party.getParty(p) != null) {
                        if (Party.getParty(p).getLeader() == p) {
                            if (args.length == 2) {
                                String playerName = args[1];
                                Player invitedPlayer = Bukkit
                                        .getPlayer(playerName);
                                if (invitedPlayer != null) {
                                    Party party = Party.getParty(p);
                                    if (!party.contains(invitedPlayer)) {
                                        // invite player
                                        party.invitePlayer(invitedPlayer);
                                        p.sendMessage(ChatColor.GREEN
                                                + RpgPlugin
                                                        .t("t_playerinvited"));
                                        invitedPlayer
                                                .sendMessage(ChatColor.GREEN
                                                        + RpgPlugin
                                                                .t("t_partyinvite")
                                                                .replace(
                                                                        "%p",
                                                                        p.getName()));
                                    } else {
                                        sender.sendMessage(ChatColor.RED
                                                + RpgPlugin
                                                        .t("t_playeralreadyinparty"));
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.RED
                                            + RpgPlugin.t("t_playernotfound"));
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED
                                        + "Invalid usage! Usage /party invite <playerName>");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED
                                    + RpgPlugin.t("t_notpartyleader"));
                        }
                    } else {
                        p.sendMessage(ChatColor.RED
                                + RpgPlugin.t("t_notinparty"));
                    }
                    break;
                case "accept":
                    if (Party.getParty(p) == null) {
                        if (args.length == 2) {
                            String leaderName = args[1];
                            Party party = Party.getParty(Bukkit
                                    .getPlayer(leaderName));
                            if (party != null) {
                                if (party.isInvited(p)) {
                                    if (party.getPlayers().size() < 3) {
                                        // join party
                                        party.addPlayer(p);
                                        sender.sendMessage(ChatColor.GREEN
                                                + RpgPlugin.t("t_joinedparty")
                                                        .replace("%p",
                                                                leaderName));
                                    } else {
                                        sender.sendMessage(ChatColor.RED
                                                + RpgPlugin.t("Party full"));
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.RED
                                            + RpgPlugin.t("t_notinvited"));
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED
                                        + RpgPlugin.t("t_partynotfound"));
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED
                                    + "Invalid usage! Usage /party accept <leaderName>");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED
                                + RpgPlugin.t("t_alreadyinparty"));
                    }
                    break;
                case "kick":
                    if (Party.getParty(p) != null) {
                        if (Party.getParty(p).getLeader() == p) {
                            if (args.length == 2) {
                                String kickedName = args[1];
                                Party party = Party.getParty(p);
                                Player kickedPlayer = Bukkit
                                        .getPlayer(kickedName);

                                if (party.contains(kickedPlayer)) {
                                    // kick player from party.
                                    party.removePlayer(kickedPlayer);
                                    p.sendMessage(ChatColor.GREEN
                                            + RpgPlugin.t("t_playerkicked")
                                                    .replace("%p", kickedName));
                                    kickedPlayer.sendMessage(ChatColor.RED
                                            + "You've been kicked from party!");
                                } else {
                                    sender.sendMessage(ChatColor.RED
                                            + RpgPlugin.t("t_playernotinparty"));
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED
                                        + "Invalid usage! Usage /party kick <playerName>");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED
                                    + RpgPlugin.t("t_notpartyleader_kick"));
                        }
                    } else {
                        p.sendMessage(ChatColor.RED
                                + RpgPlugin.t("t_notinparty"));
                    }
                    break;
                case "leave":
                    if (Party.getParty(p) != null) {
                        // leave party
                        Party.getParty(p).removePlayer(p);
                        p.sendMessage(ChatColor.GREEN
                                + RpgPlugin.t("t_leftparty"));
                    } else {
                        p.sendMessage(ChatColor.RED
                                + RpgPlugin.t("t_notinparty"));
                    }
                    break;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Wrong use!");
            }
            return true;
        }
        return false;
    }

}
