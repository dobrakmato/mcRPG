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
import eu.matejkormuth.rpgdavid.starving.remote.netty.ChannelInitializer;
import eu.matejkormuth.rpgdavid.starving.remote.netty.DefaultProtocol;
import eu.matejkormuth.rpgdavid.starving.remote.netty.Packet;
import eu.matejkormuth.rpgdavid.starving.remote.netty.handlers.PacketChannelInboundHandler;

public class ClientChannelInitializer extends ChannelInitializer {

    public ClientChannelInitializer() {
        super(new DefaultProtocol());
    }

    @Override
    protected void initChannel0(SocketChannel ch) {
        ch.pipeline().addLast(new DefaultPacketChannelInHandler());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private final class DefaultPacketChannelInHandler extends
            PacketChannelInboundHandler {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Packet msg)
                throws Exception {
            // Push to packet handler.
        }

    }

}
