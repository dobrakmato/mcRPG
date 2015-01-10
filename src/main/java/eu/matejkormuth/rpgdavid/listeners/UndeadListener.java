package eu.matejkormuth.rpgdavid.listeners;

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
		if(event.getDamager() instanceof Player) {
			if(event.getEntity() instanceof HumanEntity) {
				Profile p = RpgPlugin.getInstance().getProfile((Player) event.getDamager());
				if(p != null) {
					Character character = p.getCharacter();
					if(character == Characters.UNDEAD) {
						// TODO: Something
					}
				}
			}
		}
	}
	
	@EventHandler
	private void onEatRottenFlesh(PlayerItemConsumeEvent event){
		Profile p = RpgPlugin.getInstance().getProfile(event.getPlayer());
		if(p != null) {
			Character character = p.getCharacter();
			if(character == Characters.UNDEAD) {
				// Undeads get regeneration when consumes rotten flesh.
				if(event.getItem().getType() == Material.ROTTEN_FLESH) {
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 3, 0));
				}
			}
		}
	}
}
