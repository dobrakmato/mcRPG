package eu.matejkormuth.rpgdavid.party;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

public class Party {
	private static List<Party> parties = new ArrayList<Party>();
	
	public static Party getParty(final Player p) {
		if(p == null) {
			return null;
		}
		
		for(Party party : parties) {
			if(party.contains(p)) {
				return party;
			}
		}
		
		return null;
	}
	
	private final Set<Player> players;
	private Player leader;
	private final Set<Player> invited;
	
	public Party(final Player leader) {
		this.leader = leader;
		this.players = new HashSet<Player>();
		this.invited = new HashSet<Player>();
		
		this.players.add(leader);
		
		// Register party.
		parties.add(this);
	}
	
	public void addPlayer(final Player player) {
		this.players.add(player);
		this.broadcast("Player " + player.getDisplayName() + " joined party!");
	}
	
	public void removePlayer(final Player player) {
		this.players.remove(player);
		this.broadcast("Player " + player.getDisplayName() + " left party!");
		
		// Check if leader left party.
		if(player == this.leader) {
			this.broadcast("Party leader left the party! Party is over!");
			for(Player p : this.players) {
				this.removePlayer(p);
			}
			parties.remove(this);
		}
		
		// Check if party empty.
		if(this.players.size() == 0) {
			this.players.clear();
			this.invited.clear();
			parties.remove(this);
		}
	}
	
	public void invitePlayer(final Player player) {
		this.invited.add(player);
		this.broadcast("Player " + player.getDisplayName() + " has been invited to party!");
	}
	
	public void setLeader(final Player player) {
		this.leader = player;
		this.broadcast("Player " + player.getDisplayName() + " is now party leader!");
	}
	
	public Player getLeader() {
		return this.leader;
	}
	
	private void broadcast(final String msg) {
		for(Player p : this.players) {
			p.sendMessage(msg);
		}
	}

	public boolean contains(final Player player) {
		return this.players.contains(player);
	}

	public boolean isInvited(final Player player) {
		return this.invited.contains(player);
	}

	public Set<Player> getPlayers() {
		return this.players;
	}
}
