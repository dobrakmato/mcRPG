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
package eu.matejkormuth.rpgdavid.starving.npc.behaviours.base;

import java.util.HashMap;
import java.util.Map;

import eu.matejkormuth.rpgdavid.starving.Starving;

public class BehaviourHolder {

    private Map<Class<? extends AbstractBehaviour>, AbstractBehaviour> abstractBehaviours;

    public BehaviourHolder() {
        abstractBehaviours = new HashMap<>();
    }

    public <T extends AbstractBehaviour> T getBehaviour(Class<T> type) {
        return cast(type, abstractBehaviours.get(type));
    }

    public boolean hasBehaviour(Class<? extends AbstractBehaviour> type) {
        return abstractBehaviours.containsKey(type);
    }

    @SuppressWarnings("unchecked")
    public <T> T cast(Class<T> type, Object obj) {
        return (T) obj;
    }

    public void addBehaviour(AbstractBehaviour abstractBehaviour) {
        abstractBehaviours.put(abstractBehaviour.getClass(), abstractBehaviour);
    }

    public void tick() {
        long currentTick = Starving.ticksElapsed.get();
        for (AbstractBehaviour behaviour : abstractBehaviours.values()) {
            behaviour.tick(currentTick);
        }
    }
}
