/*
 *  mcRPG is a open source rpg bukkit/spigot plugin.
 *  Copyright (C) 2015 Matej Kormuth 
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
package eu.matejkormuth.rpgdavid.quests;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;

public abstract class AbstractQuest implements Quest {
    // Quest location.
    public double locX = 0;
    public double locY = 0;
    public double locZ = 0;
    /**
     * Quest name.
     */
    public String name = "Quest #" + this.hashCode();
    
    public NPCRegistry getNPCRegistry() {
        return CitizensAPI.getNPCRegistry();
    }
    
    @Override
    public String getName() {
        return this.name;
    }
}
