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

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Characters;
import eu.matejkormuth.rpgdavid.Dagger;
import eu.matejkormuth.rpgdavid.GrapplingHook;
import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;

public class WeaponsListener implements Listener {
    private static final double EPSILON = 5; // 5 degrees

    // Dagger
    @EventHandler
    private void onDaggerHit(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Profile p = RpgPlugin.getInstance().getProfile(
                    (Player) event.getDamager());
            if (p != null) {
                Character character = p.getCharacter();
                // Only killer.
                if (character == Characters.KILLER) {
                    // Only damage by dagger.
                    if (Dagger.isDagger(((Player) event.getDamager())
                            .getItemInHand())) {
                        // Dagger does 12 HP damage.
                        event.setDamage(12D);
                    }
                }
            }
        }
    }

    // Grappling hook
    @EventHandler
    private void onGrap(final PlayerInteractEvent event) {
        Profile p = RpgPlugin.getInstance().getProfile(event.getPlayer());
        if (p != null) {
            Character character = p.getCharacter();
            // Only killer can grap.
            if (character == Characters.KILLER) {
                if (GrapplingHook.isHook(event.getPlayer().getItemInHand())) {
                    Vector lookingDirection = event.getPlayer()
                            .getEyeLocation().getDirection().normalize();
                    for (Entity e : event.getPlayer().getNearbyEntities(
                            GrapplingHook.MAX_USE_DISTANCE,
                            GrapplingHook.MAX_USE_DISTANCE,
                            GrapplingHook.MAX_USE_DISTANCE)) {
                        if (e instanceof Player) {
                            // Find position of entity of player.
                            Vector positionVector = event.getPlayer()
                                    .getLocation().toVector()
                                    .subtract(e.getLocation().toVector())
                                    .normalize();
                            Vector difference = positionVector
                                    .subtract(lookingDirection);

                            if (difference.getX() < EPSILON
                                    && difference.getY() < EPSILON
                                    && difference.getZ() < EPSILON) {
                                // Player clicked this entity.
                                event.getPlayer().setVelocity(
                                        event.getPlayer().getLocation()
                                                .subtract(e.getLocation())
                                                .toVector());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}