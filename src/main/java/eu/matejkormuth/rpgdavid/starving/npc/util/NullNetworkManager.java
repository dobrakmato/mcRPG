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
package eu.matejkormuth.rpgdavid.starving.npc.util;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.GenericFutureListener;

import java.lang.reflect.Field;
import java.net.SocketAddress;

import javax.crypto.SecretKey;

import net.minecraft.server.v1_8_R2.ChatMessage;
import net.minecraft.server.v1_8_R2.EnumProtocol;
import net.minecraft.server.v1_8_R2.EnumProtocolDirection;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.NetworkManager;
import net.minecraft.server.v1_8_R2.Packet;
import net.minecraft.server.v1_8_R2.PacketListener;
import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;

@NMSHooks(version = "v1_8_R2")
public class NullNetworkManager extends NetworkManager {

    private static final Field CHANNEL;
    private static final Field ADDRESS;
    static {
        try {
            
            // i in 1_8_R1
            // k in 1_8_R2
            CHANNEL = NetworkManager.class
                    .getDeclaredField("k");
            if (!CHANNEL.isAccessible()) {
                CHANNEL.setAccessible(true);
            }
            
            // j in 1_8_R1
            // l in 1_8_R2
            ADDRESS = NetworkManager.class
                    .getDeclaredField("l");
            if (!ADDRESS.isAccessible()) {
                ADDRESS.setAccessible(true);
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Can't initialize Fields in NullNetworkManager!");
        }
    }

    public NullNetworkManager(EnumProtocolDirection enumprotocoldirection) {
        super(enumprotocoldirection);

        try {
            // Reflection.
            CHANNEL.set(this, new NullChannel(null));
            ADDRESS.set(this, new SocketAddress() {
                private static final long serialVersionUID = 1L;
            });
        } catch (Exception e) {
            throw new RuntimeException(
                    "Can't set field values in NullNetworkManager!");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext channelhandlercontext)
            throws Exception {
    }

    @Override
    public void a(EnumProtocol enumprotocol) {
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelhandlercontext) {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelhandlercontext,
            Throwable throwable) {
    }

    @Override
    protected void a(ChannelHandlerContext channelhandlercontext, @SuppressWarnings("rawtypes") Packet packet) {
    }

    @Override
    public void a(PacketListener packetlistener) {
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(Packet packet) {
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void a(Packet packet, GenericFutureListener genericfuturelistener,
            GenericFutureListener... agenericfuturelistener) {
    }

    @Override
    public void a() {
    }

    @Override
    public SocketAddress getSocketAddress() {
        return super.getSocketAddress();
    }

    @Override
    public void close(IChatBaseComponent ichatbasecomponent) {
    }

    @Override
    public boolean c() {
        return true;
    }

    @Override
    public void a(SecretKey secretkey) {
    }

    @Override
    public boolean g() {
        return true;
    }

    @Override
    public boolean h() {
        return true;
    }

    @Override
    public PacketListener getPacketListener() {
        return super.getPacketListener();
    }

    @Override
    public IChatBaseComponent j() {
        return new ChatMessage("");
    }

    @Override
    public void k() {
    }

    @Override
    public void a(int i) {
    }

    @Override
    public void l() {
    }

    @Override
    public SocketAddress getRawAddress() {
        return super.getRawAddress();
    }

}
