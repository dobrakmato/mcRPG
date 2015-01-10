package eu.matejkormuth.rpgdavid.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Characters;
import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;

public class VampireListener implements Listener {
	@EventHandler
	private void onVampireHitPlayer(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Profile p = RpgPlugin.getInstance().getProfile((Player) event.getDamager());
			if(p != null) {
				Character character = p.getCharacter();
				if(character == Characters.VAMPIRE) {
					// If can bite now.
					if(p.vampire_CanBite()) {
						if(event.getEntity() instanceof LivingEntity) {
							event.setDamage(3D);
							((LivingEntity)event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 4, 0));

							p.vampire_SetLastBitten();
						}
					} else {
						event.setCancelled(true);
						((Player)event.getDamager()).sendMessage(ChatColor.RED + "You can bite again after XX seconds!");
					}
				}
			}
		}
	}
}
