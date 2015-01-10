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
