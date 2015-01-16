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

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Characters;
import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;

public class KnightListener implements Listener {
    private Random random = new Random();

    @EventHandler
    private void onDamageToKnight(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Profile p = RpgPlugin.getInstance().getProfile(
                    (Player) event.getEntity());
            if (p != null) {
                Character character = p.getCharacter();
                if (character == Characters.KNIGHT) {
                    // Knight's armor have protection of breaking (implemented
                    // by chanche of giving durability back to armor).
                    if (random.nextInt(3) == 0) { // 25%
                        Player player = (Player) event.getEntity();
                        player.getInventory()
                                .getHelmet()
                                .setDurability(
                                        (short) (player.getInventory()
                                                .getHelmet().getDurability() + 1));
                        player.getInventory()
                                .getChestplate()
                                .setDurability(
                                        (short) (player.getInventory()
                                                .getChestplate()
                                                .getDurability() + 1));
                        player.getInventory()
                                .getLeggings()
                                .setDurability(
                                        (short) (player.getInventory()
                                                .getLeggings().getDurability() + 1));
                        player.getInventory()
                                .getBoots()
                                .setDurability(
                                        (short) (player.getInventory()
                                                .getBoots().getDurability() + 1));
                    }
                }
            }
        }
    }
}
