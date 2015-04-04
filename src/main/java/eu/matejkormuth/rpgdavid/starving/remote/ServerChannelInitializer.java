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

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.remote.netty.ChannelInitializer;
import eu.matejkormuth.rpgdavid.starving.remote.netty.DefaultProtocol;
import eu.matejkormuth.rpgdavid.starving.remote.netty.Packet;
import eu.matejkormuth.rpgdavid.starving.remote.netty.handlers.PacketChannelInboundHandler;
import eu.matejkormuth.rpgdavid.starving.remote.netty.packets.DisconnectPacket;
import eu.matejkormuth.rpgdavid.starving.remote.netty.packets.HandshakePacket;

public class ServerChannelInitializer extends ChannelInitializer {

    private static final AttributeKey<Boolean> HANDSHAKED = AttributeKey.valueOf("handshaked");
    private static final AttributeKey<Player> PLAYER = AttributeKey.valueOf("player");

    private Logger log;

    public ServerChannelInitializer() {
        super(new DefaultProtocol());
        this.log = Starving.getInstance().getLogger();
    }

    @Override
    protected void initChannel0(SocketChannel ch) {
        ch.pipeline().addLast(new DefaultPacketChannelInHandler());
        ch.attr(HANDSHAKED).set(false);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        if (ctx.channel().attr(HANDSHAKED).get()) {
            ctx.writeAndFlush(new DisconnectPacket("Internal Server Error: "
                    + cause.toString()));
        }
        ctx.close();
    }

    private final class DefaultPacketChannelInHandler extends
            PacketChannelInboundHandler {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Packet msg)
                throws Exception {
            if (msg instanceof HandshakePacket) {
                // Verify nickname and access key.
                if (this.verifyHandshake((HandshakePacket) msg)) {
                    // Handshake OK, continue.
                    ctx.channel().attr(HANDSHAKED).set(true);
                    ctx.channel().attr(PLAYER).set(
                            Bukkit.getPlayer(((HandshakePacket) msg).nickname));
                    log.info("Creating RconAccess for player "
                            + ((HandshakePacket) msg).nickname);
                } else {
                    // Handshake failed. Disconnect socket.
                    ctx.writeAndFlush(new DisconnectPacket(
                            "Player is not online or access key is invalid!"));
                    log.info(ctx.channel().remoteAddress().toString()
                            + " tried to create RconAccess for player "
                            + ((HandshakePacket) msg).nickname);
                    ctx.close();
                }
            } else {
                // Generic packet incoming.
                if (ctx.channel().attr(HANDSHAKED).get()) {
                    processPacket(ctx, msg);
                } else {
                    ctx.writeAndFlush(new DisconnectPacket("Not handshaked!"));
                    ctx.close();
                }
            }
        }

        private boolean verifyHandshake(HandshakePacket msg) {
            Player p = Bukkit.getPlayer(msg.nickname);
            if (p == null) {
                return false;
            }

            String accessKey = Data.of(p).getRemoteAccessKey();
            if (accessKey == null) {
                return false;
            }

            return accessKey.equals(msg.accesskey);
        }

        private void processPacket(ChannelHandlerContext ctx, Packet msg) {
            ServerPacketProcessor.incoming(ctx, ctx.attr(PLAYER).get(), msg);
        }
    }
}
