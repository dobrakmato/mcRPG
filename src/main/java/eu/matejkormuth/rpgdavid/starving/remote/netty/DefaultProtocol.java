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

import eu.matejkormuth.rpgdavid.starving.remote.netty.packets.CommandPacket;
import eu.matejkormuth.rpgdavid.starving.remote.netty.packets.DebugPacket;
import eu.matejkormuth.rpgdavid.starving.remote.netty.packets.DisconnectPacket;
import eu.matejkormuth.rpgdavid.starving.remote.netty.packets.HandshakeOkPacket;
import eu.matejkormuth.rpgdavid.starving.remote.netty.packets.HandshakePacket;
import eu.matejkormuth.rpgdavid.starving.remote.netty.packets.WGFiltersPacket;

public class DefaultProtocol extends Protocol {

    @Override
    protected void registerPackets() {
        this.register(0, HandshakePacket.class);
        this.register(1, CommandPacket.class);
        this.register(2, DisconnectPacket.class);
        this.register(3, HandshakeOkPacket.class);
        this.register(4, WGFiltersPacket.class);
        this.register(5, DebugPacket.class);
    }

}
