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

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import eu.matejkormuth.rpgdavid.spells.Spell;
import eu.matejkormuth.rpgdavid.spells.impl.FireSpell;
import eu.matejkormuth.rpgdavid.spells.impl.FreezeingSpell;

public class MagicBook extends ItemStack {
    private static final String BOOK_TITLE = "Magic Book";
    private static final String BOOK_AUTHOR = "Server";

    // Initialize spells.
    private static Spell[] spells;
    static {
        spells = new Spell[2];
        spells[0] = new FireSpell();
        spells[1] = new FreezeingSpell();
        
        // TODO: More spells.
    }

    public MagicBook() {
        super(Material.BOOK, 1);
        BookMeta im = (BookMeta) this.getItemMeta();
        im.setDisplayName(ChatColor.RESET.toString() + ChatColor.YELLOW
                + "Magic Book");
        im.setLore(Arrays.asList(ChatColor.RESET.toString() + "Level 1"));
        im.setAuthor(BOOK_AUTHOR);
        im.setTitle(BOOK_TITLE);
        im.addEnchant(Enchantment.DURABILITY, 1, true);
        this.setItemMeta(im);
    }

    @Override
    public MagicBook clone() {
        try {
            return (MagicBook) super.clone();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public int getLevel() {
        String str = this.getItemMeta().getLore().get(0);
        return Integer.valueOf(str.substring(str.indexOf("Level") + 6));
    }

    public void setLevel(final int level) {
        // Now it is book on every level. OLD: After level 5 stick is blaze rod;
        // before level 5 stick is wooden stick.
        if (level >= 5) {
            this.setType(Material.BOOK);
        } else {
            this.setType(Material.BOOK);
        }

        // Update meta.
        ItemMeta im = this.getItemMeta();
        im.setLore(Arrays.asList(ChatColor.RESET.toString() + "Level " + level));
        this.setItemMeta(im);
    }

    public static boolean isBook(final ItemStack item) {
        if (item == null) {
            return false;
        }

        if (item.getType() == Material.BOOK) {
            BookMeta bm = (BookMeta) item.getItemMeta();
            return bm.getAuthor().equals(BOOK_AUTHOR)
                    && bm.getTitle().equals(BOOK_TITLE);
        }
        return false;
    }

    public static Spell getSpell(final Player player) {
        return spells[RpgPlugin.getInstance().getProfile(player)
                .getMagican_currentSpell()];
    }

    public static Spell nextSpell(final Player player) {
        int current = RpgPlugin.getInstance().getProfile(player)
                .getMagican_currentSpell();
        if (current >= spells.length - 1) {
            RpgPlugin.getInstance().getProfile(player)
                    .setMagican_currentSpell(0);
            return spells[0];
        }
        RpgPlugin.getInstance().getProfile(player).setMagican_currentSpell(current + 1); 
        return spells[current + 1];
    }
}
