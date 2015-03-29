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
package eu.matejkormuth.rpgdavid;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import eu.matejkormuth.rpgdavid.text.TextTable;

public class Debug {
    public static void onEnable() {
        // RpgPlugin.getInstance().getQuestManager().addQuest(new TestQuest());
        Bukkit.getPluginManager().registerEvents(new DebugListener(),
                RpgPlugin.getInstance());
    }

    public static void onDisable() {

    }

    private static class DebugListener implements Listener {
        @EventHandler
        private void onChat(AsyncPlayerChatEvent event) {
            if (event.getMessage().contains("test_offer")) {
                String name = "Dark Holes";
                TextTable table = new TextTable(TextTable.MINECRAFT_CHAT_WIDTH,
                        6);
                table.renderCenteredText(1, "Quest:" + name);
                table.renderCenteredText(3, "Use /yes to accept quest.");
                table.renderCenteredText(4, "Use /no to decline quest.");
                table.formatString(1, name, ChatColor.GREEN.toString());
                table.formatLine(3, ChatColor.GREEN.toString());
                table.formatLine(4, ChatColor.RED.toString());
                table.formatBorder(ChatColor.GOLD);
                event.getPlayer().sendMessage(table.toString());
            }
        }
    }
}
