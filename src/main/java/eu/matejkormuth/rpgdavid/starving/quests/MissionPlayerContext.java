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
package eu.matejkormuth.rpgdavid.starving.quests;

import java.util.HashMap;
import java.util.Map;

public class MissionPlayerContext {

    private Map<String, Object> missionStorage;
    
    public MissionPlayerContext() {
        missionStorage = new HashMap<>();
    }

    public boolean isCompleted(Goal goal) {
        return toBool("goals." + goal.getId() + ".completed");
    }

    public void setCompleted(Goal goal, boolean completed) {
        missionStorage.put("goals." + goal.getId() + ".completed", completed);
    }

    public boolean isCompleted(Mission mission) {
        return toBool("missions." + mission.getId() + ".completed");
    }

    public void setCompleted(Mission mission, boolean completed) {
        missionStorage.put("missions." + mission.getId() + ".completed", completed);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) missionStorage.get(key);
    }
    
    public void set(String key, Object value) {
        missionStorage.put(key, value);
    }
    
    public Map<String, Object> getMissionStorage() {
        return missionStorage;
    }
    
    private boolean toBool(String key) {
        if(!missionStorage.containsKey(key)) {
            return false;
        }
        
        Object val = missionStorage.get(key);
        
        if(val == null) {
            return false;
        }
        
        if(val instanceof Number) {
            return ((Number) val).doubleValue() < 0;
        }
        
        if(val instanceof Boolean) {
            return ((Boolean) val).booleanValue();
        }
        
        return true;
    }

}
