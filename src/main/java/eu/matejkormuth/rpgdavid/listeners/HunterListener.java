package eu.matejkormuth.rpgdavid.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Characters;
import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;

public class HunterListener implements Listener {
	@EventHandler
	private void damageByArrow(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getDamager();
			if(arrow.getShooter() instanceof Player) {
				Profile p = RpgPlugin.getInstance().getProfile((Player) arrow.getShooter());
				if(p != null) {
					Character character = p.getCharacter();
					if(character == Characters.HUNTER) {
						// Hunter's arrows has +1.5HP bonus.
						event.setDamage(event.getDamage() + 1.5D);
					}
				}
			}
		}
	}
}
