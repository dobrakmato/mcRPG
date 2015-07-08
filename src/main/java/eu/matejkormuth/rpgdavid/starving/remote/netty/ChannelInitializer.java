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
package eu.matejkormuth.rpgdavid.starving.remote.netty;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import eu.matejkormuth.rpgdavid.starving.remote.netty.handlers.ByteBufToPacketEncoder;
import eu.matejkormuth.rpgdavid.starving.remote.netty.handlers.ByteBufToPacketHandler;
import eu.matejkormuth.rpgdavid.starving.remote.netty.handlers.ShortHeaderFrameDecoder;
import eu.matejkormuth.rpgdavid.starving.remote.netty.handlers.ShortHeaderFrameEncoder;

public abstract class ChannelInitializer extends
        io.netty.channel.ChannelInitializer<SocketChannel> {

    private Protocol protocol;

    public ChannelInitializer(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ShortHeaderFrameDecoder());
        pipeline.addLast(new ShortHeaderFrameEncoder());

        pipeline.addLast(new ByteBufToPacketHandler(new PacketFactory(protocol)));
        pipeline.addLast(new ByteBufToPacketEncoder(protocol));

        initChannel0(ch);
    }

    protected abstract void initChannel0(SocketChannel ch);
}
