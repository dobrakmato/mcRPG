package eu.matejkormuth.rpgdavid.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.party.Party;

public class PartyCommandExecutor implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (command.getName().equals("party")) {
			if (args.length > 0) {
				if(!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "This command can be only executed as player!");
					return true;
				}
				Player p = (Player)sender;
				
				String subcommand = args[0];
				switch (subcommand) {
				case "create":
					if(Party.getParty(p) == null) {
						// create party
						new Party(p);
					} else {
						p.sendMessage(ChatColor.RED + "You are already in party! To leave current party type /party leave!");
					}
					break;
				case "invite":
					if(Party.getParty(p) != null) {
						if(Party.getParty(p).getLeader() == p) {
							if(args.length == 2) {
								String playerName = args[1];
								Player invitedPlayer = Bukkit.getPlayer(playerName);
								if(invitedPlayer != null) {
									Party party = Party.getParty(p);
									if(!party.contains(invitedPlayer)) {
										// invite player
										party.invitePlayer(invitedPlayer);
										p.sendMessage(ChatColor.GREEN + "Player " + playerName + " has been invited to your party!");
										invitedPlayer.sendMessage(ChatColor.GREEN + "Player " + p.getName() + " invites you to his party! Type /party accept " + p.getName() + " to join his party!");
									} else {
										sender.sendMessage(ChatColor.RED + "Specified player is already in party!");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "Specified player is not found!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "Invalid usage! Usage /party invite <playerName>");
							}
						} else {
							p.sendMessage(ChatColor.RED + "You have to be party leader in order to invite people to party!");
						}
					} else {
						p.sendMessage(ChatColor.RED + "You are not in party! Type /party create to create a party!");
					}
					break;
				case "accept":
					if(Party.getParty(p) == null) {
						if(args.length == 2) {
							String leaderName = args[1];
							Party party = Party.getParty(Bukkit.getPlayer(leaderName)); 
							if(party != null) {
								if(party.isInvited(p)) {
									if(party.getPlayers().size() < 3) {
										// join party
										party.addPlayer(p);
										sender.sendMessage(ChatColor.GREEN + "You have joined " + leaderName + "'s party!");
									} else {
										sender.sendMessage(ChatColor.RED + "This party is full!");
									}
								} else {
									sender.sendMessage(ChatColor.RED + "You are not invited to this party!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "Specified party does not exists!");
							}
						} else {
							sender.sendMessage(ChatColor.RED + "Invalid usage! Usage /party accept <leaderName>");
						}
					} else {
						p.sendMessage(ChatColor.RED + "You are already in party! To leave current party type /party leave!");
					}
					break;
				case "kick":
					if(Party.getParty(p) != null) {
						if(Party.getParty(p).getLeader() == p) {
							if(args.length == 2) {
								String kickedName = args[1];
								Party party = Party.getParty(p);
								Player kickedPlayer = Bukkit.getPlayer(kickedName);
								
								if(party.contains(kickedPlayer)) {
									// kick player from party.
									party.removePlayer(kickedPlayer);
									p.sendMessage(ChatColor.GREEN + "Player " + kickedName + " has been kicked!");
									kickedPlayer.sendMessage(ChatColor.RED + "You've been kicked from party!");
								} else {
									sender.sendMessage(ChatColor.RED + "Specified player is not in your party!");
								}
							} else {
								sender.sendMessage(ChatColor.RED + "Invalid usage! Usage /party kick <playerName>");
							}
						} else {
							p.sendMessage(ChatColor.RED + "You have to be party leader in order to kick people from party!");
						}
					} else {
						p.sendMessage(ChatColor.RED + "You are not in party! Type /party create to create a party!");
					}
					break;
				case "leave":
					if(Party.getParty(p) != null) {
						// leave party
						Party.getParty(p).removePlayer(p);
						p.sendMessage(ChatColor.GREEN + "You have left the party!");
					} else {
						p.sendMessage(ChatColor.RED + "You are not in party!");
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
