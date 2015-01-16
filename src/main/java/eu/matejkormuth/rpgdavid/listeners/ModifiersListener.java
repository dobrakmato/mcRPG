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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;

public class ModifiersListener implements Listener {
	private Random random = new Random();
	
	@EventHandler
	private void onEntityDmgEntity(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Profile p = RpgPlugin.getInstance().getProfile((Player) event.getDamager());
			if(p != null) {
				// Only to players with character.
				if(p.hasCharacter()) {
					// Damage modifier.
					Character character = p.getCharacter();
					event.setDamage(event.getDamage() * character.getModifiers().getDamageModifier());
					
					// Critical modifier.
					if(random.nextFloat() < (character.getModifiers().getCriticalModifier() - 1F)) {
						// Critical hit is 150% damage.
						event.setDamage(event.getDamage() * 1.5F);
					}
				}
			}
		}
	}
	
	@EventHandler
	private void onRegainHealth(EntityRegainHealthEvent event) {
		if(event.getEntity() instanceof Player) {
			Profile p = RpgPlugin.getInstance().getProfile((Player) event.getEntity());
			if(p != null) {
				if(p.hasCharacter()) {
					Character character = p.getCharacter();
					event.setAmount(event.getAmount() * character.getModifiers().getHealthRegainModifier());
				}
			}
		}
	}
}
