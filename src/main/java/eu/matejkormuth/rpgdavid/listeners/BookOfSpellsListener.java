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
package eu.matejkormuth.rpgdavid.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Characters;
import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.items.BookOfSpells;
import eu.matejkormuth.rpgdavid.spells.Spell;

public class BookOfSpellsListener implements Listener {
    @EventHandler
    private void onSpellCast(final PlayerInteractEvent event) {
        Profile p = RpgPlugin.getInstance().getProfile(event.getPlayer());
        if (p != null) {
            Character character = p.getCharacter();
            // Only magican.
            if (character == Characters.MAGICAN) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR
                        || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (event.hasItem() && BookOfSpells.isBook(event.getItem())) {
                        // Find the right spell.
                        Spell spell = BookOfSpells.getSpell(event.getPlayer());
                        // Check if player has enough xp to to cast this spell.
                        if (BookOfSpells.getLevel(event.getItem()) >= spell
                                .getMinLevel()) {
                            // Cast the spell.
                            spell.cast(event.getPlayer());
                        } else {
                            event.getPlayer()
                                    .sendMessage(
                                            ChatColor.RED
                                                    + RpgPlugin
                                                            .t("t_lowlevel")
                                                            .replace(
                                                                    "%l",
                                                                    ""
                                                                            + spell.getMinLevel()));
                        }
                    }
                }
            }
        }
    }

    // Used to update book, when player switches to it.
    @EventHandler
    private void onSelectedItemChanged(final PlayerItemHeldEvent event) {
        Profile p = RpgPlugin.getInstance().getProfile(event.getPlayer());
        if (p != null) {
            Character character = p.getCharacter();
            // Only magican.
            if (character == Characters.MAGICAN) {
                ItemStack is = event.getPlayer().getInventory()
                        .getItem(event.getNewSlot());
                if (BookOfSpells.isBook(is)) {
                    // Perform level computation.
                    int level = 1 + (int) (p.getXp() / 10);
                    BookOfSpells.setLevel(is, level);
                }
            }
        }
    }

    @EventHandler
    private void onSpellSwitch(final PlayerToggleSneakEvent event) {
        Profile p = RpgPlugin.getInstance().getProfile(event.getPlayer());
        if (p != null) {
            Character character = p.getCharacter();
            if (character == Characters.MAGICAN) {
                if (event.getPlayer().getItemInHand() != null
                        && BookOfSpells.isBook(event.getPlayer()
                                .getItemInHand())) {
                    // Prevent dupe switch.
                    if (event.isSneaking()) {
                        // Cycle trough spells only.
                        Spell spell = BookOfSpells.nextSpell(event.getPlayer());
                        event.getPlayer().sendMessage(
                                ChatColor.YELLOW
                                        + RpgPlugin.t("t_currentspell")
                                        + spell.getName());
                    }
                }
            }
        }
    }
}
