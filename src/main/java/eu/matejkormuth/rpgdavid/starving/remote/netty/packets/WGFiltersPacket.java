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

import java.util.Collection;

import eu.matejkormuth.rpgdavid.starving.remote.netty.Packet;
import eu.matejkormuth.rpgdavid.starving.worldgen.filters.base.Filter;
import eu.matejkormuth.rpgdavid.starving.worldgen.filters.base.FilterProperty;

public class WGFiltersPacket extends Packet {

    public FilterRepresentation[] filters;

    public WGFiltersPacket() {
    }

    public WGFiltersPacket(Collection<Filter> filters) {
        int count = filters.size();
        this.filters = new FilterRepresentation[count];
        int index = 0;
        for (Filter f : filters) {
            this.filters[index] = new FilterRepresentation();
            this.filters[index].name = f.getName();
            Collection<FilterProperty> props = f.getDefaultProperties().getProperties().values();
            FilterPropertyRepresentaion[] rprops = new FilterPropertyRepresentaion[props.size()];
            int propIndex = 0;
            for (FilterProperty fp : props) {
                rprops[propIndex] = new FilterPropertyRepresentaion();
                rprops[propIndex].name = fp.getName();
                rprops[propIndex].type = (byte) (fp.getType() - 1000);
                propIndex++;
            }
            index++;
        }
    }

    @Override
    public void writeTo(ByteBuf toBuffer) {
        toBuffer.writeByte(filters.length);
        for (FilterRepresentation fr : filters) {
            toBuffer.writeByte(fr.name.length());
            toBuffer.writeBytes(fr.name.getBytes(PROTOCOL_ENCODING));
            toBuffer.writeByte(fr.properties.length);
            for (FilterPropertyRepresentaion fpr : fr.properties) {
                toBuffer.writeByte(fpr.name.length());
                toBuffer.writeBytes(fpr.name.getBytes(PROTOCOL_ENCODING));
                toBuffer.writeByte(fpr.type);
            }
        }
    }

    @Override
    public void readFrom(ByteBuf fromBuffer) {
        byte filterCount = fromBuffer.readByte();
        FilterRepresentation[] filters = new FilterRepresentation[filterCount];
        for (int i = 0; i < filterCount; i++) {
            filters[i] = new FilterRepresentation();
            byte nameLength = fromBuffer.readByte();
            byte[] nameBuffer = new byte[nameLength];
            fromBuffer.readBytes(nameBuffer);
            filters[i].name = new String(nameBuffer, PROTOCOL_ENCODING);
            byte propertiesCount = fromBuffer.readByte();
            filters[i].properties = new FilterPropertyRepresentaion[propertiesCount];
            for (int p = 0; p < propertiesCount; p++) {
                byte propertyNameLength = fromBuffer.readByte();
                byte[] propertyNameBuffer = new byte[propertyNameLength];
                filters[i].properties[p] = new FilterPropertyRepresentaion();
                filters[i].properties[p].name = new String(propertyNameBuffer,
                        PROTOCOL_ENCODING);
                filters[i].properties[p].type = fromBuffer.readByte();
            }
        }
    }

    public final static class FilterRepresentation {
        String name;
        FilterPropertyRepresentaion[] properties;
    }

    public final static class FilterPropertyRepresentaion {
        byte type;
        String name;
    }
}
