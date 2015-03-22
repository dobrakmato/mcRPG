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

import java.io.File;
import java.util.EnumMap;
import java.util.Map.Entry;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;

public class XPListener implements Listener {
	private static EnumMap<EntityType, Integer> xps;

	static {
		xps = new EnumMap<>(EntityType.class);
		loadXpMap();
	}

	private static void loadXpMap() {
		File f = RpgPlugin.getInstance().getFile("xps.yml");
		if (!f.exists()) {
			RpgPlugin
					.getInstance()
					.getLogger()
					.severe("XP configuration file (xps.yml) not found! Plugin may not function properly!");
		} else {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
			for (Entry<String, Object> value : config.getValues(false)
					.entrySet()) {
				xps.put(EntityType.valueOf(value.getKey()),
						Integer.valueOf(value.getValue().toString()));
			}
		}
	}

	@EventHandler
	private void onEntityKilled(final EntityDamageByEntityEvent event) {
		// Only living entities.
		if (event.getEntity() instanceof LivingEntity) {
			// If is this killing hit.
			if (((Damageable) event.getEntity()).getHealth()
					- event.getDamage() <= 0) {
				// If damager is player.
				if (event.getDamager() instanceof Player) {
					Profile p = RpgPlugin.getInstance().getProfile(
							(Player) event.getDamager());
					if (p != null) {
						// Give XP.
						p.giveXp(xps.get(event.getEntityType()));
					}
				}
			}
		}
	}
}
