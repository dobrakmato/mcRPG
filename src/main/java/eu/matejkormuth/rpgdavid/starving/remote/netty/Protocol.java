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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Protocol {

    private Map<Class<? extends Packet>, Constructor<? extends Packet>> constructorsByClass;
    private Map<Short, Constructor<? extends Packet>> constructorsByInt;
    private Map<Class<? extends Packet>, Short> idsByClass;

    public Protocol() {
        constructorsByClass = new HashMap<>();
        constructorsByInt = new HashMap<>();
        idsByClass = new HashMap<>();

        registerPackets();
    }

    protected abstract void registerPackets();

    public void register(int id, Class<? extends Packet> type) {
        if (id > Short.MAX_VALUE) {
            throw new IllegalArgumentException(
                    "id must be lower then Short.MAX_VALUE");
        }

        try {
            Constructor<? extends Packet> noArgCtr = type.getConstructor();
            if (noArgCtr.isAccessible()) {
                throw new RuntimeException(
                        "Argless contructor is not accessible!");
            }

            constructorsByClass.put(type, noArgCtr);
            constructorsByInt.put((short) id, noArgCtr);
            idsByClass.put(type, (short) id);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(
                    "All packets must declare public argless contructor!", e);
        }
    }

    public <T extends Packet> T create(Class<T> type) {
        if (this.constructorsByClass.containsKey(type)) {
            try {
                Packet packet = this.constructorsByClass.get(type)
                        .newInstance();
                return unsafeCast(packet, type);
            } catch (InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException("Can't create packet of type "
                        + type.getName(), e);
            }
        } else {
            throw new RuntimeException("No such packet of type "
                    + type.getName() + " is registered in this PacketFactory!");
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Packet> T create(short id) {
        if (this.constructorsByInt.containsKey(id)) {
            try {
                Packet packet = this.constructorsByInt.get(id)
                        .newInstance();
                return (T) packet;
            } catch (InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException("Can't create packet of id "
                        + id, e);
            }
        } else {
            throw new RuntimeException("No such packet of type "
                    + id + " is registered in this PacketFactory!");
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T unsafeCast(Object o, Class<T> type) {
        return (T) o;
    }

    public int getId(Packet msg) {
        Class<?> clazz = msg.getClass();
        if (this.idsByClass.containsKey(clazz)) {
            return this.idsByClass.get(clazz);
        }
        else {
            throw new IllegalArgumentException("Packet '"
                    + msg.getClass().getName()
                    + "' is not recognized by protocol "
                    + this.getClass().getName() + "!");
        }
    }
}
