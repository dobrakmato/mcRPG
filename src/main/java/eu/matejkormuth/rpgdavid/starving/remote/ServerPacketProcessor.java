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

import io.netty.channel.ChannelHandlerContext;
import eu.matejkormuth.rpgdavid.starving.remote.netty.Packet;
import eu.matejkormuth.rpgdavid.starving.remote.netty.packets.CommandPacket;
import eu.matejkormuth.rpgdavid.starving.remote.netty.packets.DisconnectPacket;

public final class ServerPacketProcessor {

    private ServerPacketProcessor() {
    }

    public static final void incoming(ChannelHandlerContext ctx, Player player,
            Packet msg) {
        // Check if player is online.
        if (!player.isOnline()) {
            ctx.writeAndFlush(new DisconnectPacket(
                    "Player disconnected from server!"));
            ctx.close();
            return;
        }

        if (msg instanceof CommandPacket) {
            player.performCommand(((CommandPacket) msg).command);
        }
    }

}
