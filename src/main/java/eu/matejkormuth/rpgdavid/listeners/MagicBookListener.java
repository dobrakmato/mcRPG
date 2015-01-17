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
import org.bukkit.event.player.PlayerToggleSneakEvent;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Characters;
import eu.matejkormuth.rpgdavid.MagicBook;
import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.spells.Spell;

public class MagicBookListener implements Listener {
    @EventHandler
    private void onSpellCast(final PlayerInteractEvent event) {
        Profile p = RpgPlugin.getInstance().getProfile(event.getPlayer());
        if (p != null) {
            Character character = p.getCharacter();
            // Only magican.
            if (character == Characters.MAGICAN) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR
                        || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (event.hasItem() && MagicBook.isBook(event.getItem())) {
                        // Find the right spell.
                        Spell spell = MagicBook.getSpell(event.getPlayer());
                        // Cast the spell.
                        spell.cast(event.getPlayer());
                    }
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
                        && MagicBook.isBook(event.getPlayer().getItemInHand())) {
                    // Prevent dupe switch.
                    if (event.isSneaking()) {
                        // Cycle trough spells only.
                        Spell spell = MagicBook.nextSpell(event.getPlayer());
                        event.getPlayer().sendMessage(
                                ChatColor.YELLOW + "Current spell: "
                                        + spell.getName());
                    }
                }
            }
        }
    }
}
