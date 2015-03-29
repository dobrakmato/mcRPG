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

import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.remote.Connection.ConnectionCallback;

/**
 * Represents access to server by one of players.
 */
public class PlayerAccess implements ConnectionCallback {

    private Player player;
    private Connection connection;

    public PlayerAccess(Player player, Connection connection) {
        this.player = player;
        this.connection = connection;
        connection.startReading(this);
    }

    public void executeCommand(String command) {
        this.player.performCommand(command);
    }

    @Override
    public void onLine(String line) {
        if(line.startsWith("COMMAND|")) {
            String command = line.substring(8);
            executeCommand(command);
        } else {
            // Ignore.
        }
    }

    public void onDisconnect() {
        this.player = null;
        this.connection.disconnect("Player disconnected");
    }
}
