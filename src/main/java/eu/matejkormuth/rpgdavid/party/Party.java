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
package eu.matejkormuth.rpgdavid.party;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.RpgPlugin;

public class Party {
    private static List<Party> parties = new ArrayList<Party>();

    public static Party getParty(final Player p) {
        if (p == null) {
            return null;
        }

        for (Party party : parties) {
            if (party.contains(p)) {
                return party;
            }
        }

        return null;
    }

    public static void clearParties() {
        parties.clear();
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
        this.broadcast(RpgPlugin.t("t_playerjoined").replace("%p",
                player.getName()));
    }

    public void removePlayer(final Player player) {
        this.players.remove(player);
        this.broadcast(RpgPlugin.t("t_playerleft").replace("%p",
                player.getName()));

        // Check if leader left party.
        if (player == this.leader) {
            this.broadcast(RpgPlugin.t("t_leaderleft"));
            for (Player p : this.players) {
                this.removePlayer(p);
            }
            parties.remove(this);
        }

        // Check if party empty.
        if (this.players.size() == 0) {
            this.players.clear();
            this.invited.clear();
            parties.remove(this);
        }
    }

    public void invitePlayer(final Player player) {
        this.invited.add(player);
        this.broadcast(RpgPlugin.t("t_playerinvited").replace("%p",
                player.getName()));
    }

    public void setLeader(final Player player) {
        this.leader = player;
        this.broadcast(RpgPlugin.t("t_newpartyleader").replace("%p",
                player.getName()));
    }

    public Player getLeader() {
        return this.leader;
    }

    private void broadcast(final String msg) {
        for (Player p : this.players) {
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
