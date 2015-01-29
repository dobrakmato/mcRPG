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
package eu.matejkormuth.rpgdavid.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.quests.Quest;

public class YesCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;

        // If has offered quest.
        if (RpgPlugin.getInstance().getQuestManager().hasOffer(player)) {
            Quest quest = RpgPlugin.getInstance().getQuestManager()
                    .removeOffer(player);
            // Start quest
            if (RpgPlugin.getInstance().getProfile(player)
                    .addActiveQuest(quest.getId())) {
                quest.onStart(player);
                sender.sendMessage(ChatColor.GREEN + "You accepted quest!");
            } else {
                sender.sendMessage(ChatColor.RED
                        + "You already accepted this quest!");
            }
        } else {
            sender.sendMessage(ChatColor.RED
                    + "You don't have any offered quest!");
        }
        return true;
    }
}
