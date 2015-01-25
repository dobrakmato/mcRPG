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

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.entity.EntityHumanNPC.PlayerNPC;

public abstract class AbstractQuest implements Quest {
    // Quest location.
    public double locX = 0;
    public double locY = 0;
    public double locZ = 0;
    /**
     * Quest name.
     */
    public String name = "Quest #" + this.hashCode();
    /**
     * Quest ID.
     */
    public String id = "quest_" + this.hashCode();
    /**
     * Id of quest that must be completed for start of this quest.
     */
    private String previousId = null;

    public NPCRegistry getNPCRegistry() {
        return CitizensAPI.getNPCRegistry();
    }

    public World world(String name) {
        return Bukkit.getWorld(name);
    }

    public Location location(String worldname, double x, double y, double z) {
        return new Location(world(worldname), x, y, z);
    }

    @SuppressWarnings("deprecation")
    public void take(Player player, int type, byte data, short damage,
            int amount) {
        player.getInventory().removeItem(
                new ItemStack(type, amount, damage, data));
    }

    @SuppressWarnings("deprecation")
    public void give(Player player, int type, byte data, short damage,
            int amount) {
        player.getInventory()
                .addItem(new ItemStack(type, amount, damage, data));
    }

    @SuppressWarnings("deprecation")
    public void has(Player player, int type, byte data, short damage, int amount) {
        player.getInventory().contains(
                new ItemStack(type, amount, damage, data));
    }

    public void chat(Player player, String message) {
        player.sendMessage(message);
    }

    public PlayerNPC npc_spawn(String name) {
        return (PlayerNPC) this.getNPCRegistry().createNPC(EntityType.PLAYER,
                name);
    }

    public void npc_teleport(NPC npc, Location loc) {
        npc.teleport(loc, TeleportCause.PLUGIN);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getPreviousId() {
        return this.previousId;
    }
}
