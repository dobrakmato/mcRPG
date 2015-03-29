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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class VampireListener implements Listener {
    @EventHandler
    private void onVampireHitPlayer(EntityDamageByEntityEvent event) {

        // Biting has been removed on 3. Feb. 2015

        /*
         * if (event.getDamager() instanceof Player) { Profile p =
         * RpgPlugin.getInstance().getProfile( (Player) event.getDamager()); if
         * (p != null) { Character character = p.getCharacter(); if (character
         * == Characters.VAMPIRE) { // If can bite now. if (p.canBite()) { if
         * (event.getEntity() instanceof LivingEntity) { event.setDamage(3D);
         * ((LivingEntity) event.getEntity()) .addPotionEffect(new PotionEffect(
         * PotionEffectType.CONFUSION, 20 * 4, 0)); p.setLastBittenNow(); } }
         * else { event.setCancelled(true); long time = p.getLastBitten() +
         * 60000 - System.currentTimeMillis(); ((Player)
         * event.getDamager()).sendMessage(ChatColor.RED +
         * "You can bite again after " + (time / 1000) + " seconds!"); } } } }
         */
    }
}
