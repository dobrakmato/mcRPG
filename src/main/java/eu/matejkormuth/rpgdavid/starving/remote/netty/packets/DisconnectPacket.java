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

public class DisconnectPacket extends Packet {

    public String reason;

    // Need to have argless constructor.
    public DisconnectPacket() {
    }
    
    public DisconnectPacket(String reason) {
        this.reason = reason;
    }

    @Override
    public void writeTo(ByteBuf toBuffer) {
        toBuffer.writeShort(reason.length());
        toBuffer.writeBytes(reason.getBytes(PROTOCOL_ENCODING));
    }

    @Override
    public void readFrom(ByteBuf fromBuffer) {
        short length = fromBuffer.readShort();
        byte[] array = new byte[length];
        fromBuffer.readBytes(array);
        this.reason = new String(array, PROTOCOL_ENCODING);
    }

}
