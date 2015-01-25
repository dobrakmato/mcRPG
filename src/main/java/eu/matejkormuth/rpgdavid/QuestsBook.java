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

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import eu.matejkormuth.rpgdavid.quests.Quest;

public class QuestsBook extends ItemStack {
    private static final String BOOK_TITLE = "Quests Book";
    private static final String BOOK_AUTHOR = "Server";

    public QuestsBook() {
        super(Material.WRITTEN_BOOK, 1);
        BookMeta im = (BookMeta) this.getItemMeta();
        im.setDisplayName(ChatColor.RESET.toString() + ChatColor.LIGHT_PURPLE
                + "Quests Book");
        im.setAuthor(BOOK_AUTHOR);
        im.setTitle(BOOK_TITLE);
        im.addPage("I you see this, you have lag.");
        this.setItemMeta(im);
    }

    @Override
    public QuestsBook clone() {
        try {
            return (QuestsBook) super.clone();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public static boolean isQuestBook(final ItemStack item) {
        if (item == null) {
            return false;
        }

        if (item.getType() == Material.WRITTEN_BOOK) {
            BookMeta bm = (BookMeta) item.getItemMeta();
            return bm.getAuthor().equals(BOOK_AUTHOR)
                    && bm.getTitle().equals(BOOK_TITLE);
        }
        return false;
    }

    public static void update(final Player player, final ItemStack item) {
        if (item == null) {
            return;
        }

        if (item.getType() == Material.WRITTEN_BOOK) {
            BookMeta bm = (BookMeta) item.getItemMeta();

            bm.setAuthor(BOOK_AUTHOR);
            bm.setTitle(BOOK_TITLE);

            StringBuilder builder = new StringBuilder();

            builder.append(ChatColor.BOLD);
            builder.append("Quests: \n");
            builder.append(ChatColor.RESET);

            Profile p = RpgPlugin.getInstance().getProfile(player);

            // For each quest.
            for (Quest quest : RpgPlugin.getInstance().getQuestManager()
                    .getQuests()) {
                builder.append(ChatColor.BLUE);
                builder.append(quest.getName());
                if (p.isQuestCompleted(quest.getId())) {
                    builder.append(ChatColor.GREEN);
                    builder.append(" [✔]\n");
                } else {
                    builder.append(ChatColor.RED);
                    builder.append(" [✗]\n");
                }

            }

            bm.setPages(builder.toString());
            item.setItemMeta(bm);
        }
    }
}
