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
package eu.matejkormuth.rpgdavid.starving;

import java.lang.reflect.Field;
import java.util.Map;

import net.minecraft.server.v1_8_R2.EntityTypes;
import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;
import eu.matejkormuth.rpgdavid.starving.zombie.Zombie;

@NMSHooks(version = "v1_8_R2")
public class ServerZombiePatcher {
    public void patchAll() {
        Starving.getInstance().getLogger()
                .info("[Patcher] Applying changes in server...");
        patchZombies();
    }

    private void patchZombies() {
        Starving.getInstance().getLogger()
                .info("[Patcher] Patching zombies...");
        patchEntity(Zombie.class, "Zombie", 54);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void patchEntity(Class<?> entityClass, String name, int id) {
        Starving.getInstance().getLogger()
                .info("[Patcher] Patching entity: " + name);
        // According to some online resource, we must put values in 'd' and 'f'
        // fields.
        ((Map) getField("d", EntityTypes.class, null)).put(entityClass, name);
        ((Map) getField("f", EntityTypes.class, null)).put(entityClass,
                Integer.valueOf(id));
    }

    private Object getField(String fieldName, Class<?> clazz, Object object) {
        try {
            Field f = clazz.getDeclaredField(fieldName);

            if (!f.isAccessible()) {
                f.setAccessible(true);
            }

            return f.get(object);
        } catch (NoSuchFieldException | SecurityException
                | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

    }
}
