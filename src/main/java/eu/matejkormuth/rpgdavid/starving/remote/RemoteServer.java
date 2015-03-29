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
package eu.matejkormuth.rpgdavid.starving.remote;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.Starving;

public class RemoteServer implements Listener {

    private ServerThread thread;
    private Map<Player, PlayerAccess> accesses;

    public RemoteServer() {
        thread = new ServerThread();
        accesses = new HashMap<>();
    }

    public void start() {
        thread.start();
    }

    @EventHandler
    private void onPlayerQuit(final PlayerQuitEvent event) {
        accesses.remove(event.getPlayer()).onDisconnect();
    }

    public boolean hasAccess(Player player) {
        return this.accesses.containsKey(player);
    }

    public PlayerAccess getAccess(Player player) {
        return this.accesses.get(player);
    }

    private class ServerThread extends Thread {

        private ServerSocket listenSocket;
        private boolean running = true;

        public ServerThread() {
            super("RemoteServer-Listen");
            this.setDaemon(true);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    this.listen();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private void listen() throws IOException {
            while (running) {
                Socket socketConnection = listenSocket.accept();
                // Handshake.
                Connection connection = new Connection(socketConnection);
                String playerName = connection.read();
                String key = connection.read();

                Player p = Bukkit.getPlayer(playerName);

                if (p.isOnline() && !p.isBanned()) {
                    Data d = Data.of(p);
                    // Check for match.
                    if (key.equals(d.getRemoteAccessKey())) {
                        // Key match.
                        PlayerAccess access = new PlayerAccess(p, connection);
                        accesses.put(p, access);
                        Starving.getInstance().getLogger().info(
                                "PlayerAccess was created for player '" + playerName
                                        + "'.");
                    } else {
                        connection.disconnect("Invalid access key for player '"
                                + playerName + "'!");
                    }

                } else {
                    connection.disconnect("Player '" + playerName
                            + "' is not online or is banned!");
                }

            }
        }

        public void shutdown() {
            this.running = false;
        }

    }

    public void stop() {
        thread.shutdown();
    }
}
