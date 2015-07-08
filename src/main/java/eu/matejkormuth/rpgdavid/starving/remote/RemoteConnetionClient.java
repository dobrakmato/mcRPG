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

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;

public class RemoteConnetionClient {

    // Netty stuff.
    private NioEventLoopGroup eventGroup;
    private Bootstrap bootstrap;

    public RemoteConnetionClient() {
        initializeNetty();
    }

    private void initializeNetty() {
        this.eventGroup = new NioEventLoopGroup(1);

        this.bootstrap = new Bootstrap();
        this.bootstrap
                .group(eventGroup)
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelInitializer())
                .option(ChannelOption.TCP_NODELAY, false)
                .option(ChannelOption.SO_BACKLOG, 128);

    }

    public ChannelFuture connect(String host, int port) {
        return this.bootstrap.connect(host, port);
    }
    
    public Future<?> shutdown() {
        return this.eventGroup.shutdownGracefully();
    }
}
