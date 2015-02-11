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
package eu.matejkormuth.rpgdavid.listeners.characters;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Characters;
import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;

public class UndeadListener implements Listener {
    @EventHandler
    private void onKill(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (event.getEntity() instanceof HumanEntity) {
                Profile p = RpgPlugin.getInstance().getProfile(
                        (Player) event.getDamager());
                if (p != null) {
                    Character character = p.getCharacter();
                    if (character == Characters.UNDEAD) {
                        // TODO: Something
                    }
                }
            }
        }
    }

    @EventHandler
    private void onEatRottenFlesh(PlayerItemConsumeEvent event) {
        Profile p = RpgPlugin.getInstance().getProfile(event.getPlayer());
        if (p != null) {
            Character character = p.getCharacter();
            if (character == Characters.UNDEAD) {
                // Undeads get regeneration when consumes rotten flesh.
                if (event.getItem().getType() == Material.ROTTEN_FLESH) {
                    event.getPlayer().addPotionEffect(
                            new PotionEffect(PotionEffectType.REGENERATION,
                                    20 * 7, 2));
                }
            }
        }
    }
}
