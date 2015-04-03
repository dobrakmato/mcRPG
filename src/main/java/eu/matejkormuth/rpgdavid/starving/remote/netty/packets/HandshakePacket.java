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
package eu.matejkormuth.rpgdavid.starving.remote.netty.packets;

import io.netty.buffer.ByteBuf;
import eu.matejkormuth.rpgdavid.starving.remote.netty.Packet;

public class HandshakePacket extends Packet {

    public String nickname;
    public String accesskey;

    @Override
    public void writeTo(ByteBuf toBuffer) {
        toBuffer.writeByte(nickname.length());
        toBuffer.writeBytes(nickname.getBytes(PROTOCOL_ENCODING));
        toBuffer.writeByte(accesskey.length());
        toBuffer.writeBytes(accesskey.getBytes(PROTOCOL_ENCODING));
    }

    @Override
    public void readFrom(ByteBuf fromBuffer) {
        byte[] nickname = new byte[fromBuffer.readByte()];
        fromBuffer.readBytes(nickname);
        byte[] accesskey = new byte[fromBuffer.readByte()];
        fromBuffer.readBytes(accesskey);
        this.nickname = new String(nickname, PROTOCOL_ENCODING);
        this.accesskey = new String(accesskey, PROTOCOL_ENCODING);
    }

}
