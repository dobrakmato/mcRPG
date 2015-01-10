package eu.matejkormuth.rpgdavid.listeners;

import java.util.Random;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Characters;
import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;

public class AdventurerListener implements Listener {
	private Random random = new Random();
	
	@EventHandler
	private void onFoodLevelChange(FoodLevelChangeEvent event) {
		if(event.getEntity() instanceof Player) {
			Profile p = RpgPlugin.getInstance().getProfile((Player) event.getEntity());
			if(p != null) {
				Character character = p.getCharacter();
				if(character == Characters.ADVENTURER) {
					// Adventurer has 2 times slower food level lowering.
					event.setCancelled(this.random.nextBoolean());
				}
			}
		}
	}
	
	@EventHandler
	private void onHitByPotion(PotionSplashEvent event) {
		if(event.getPotion().getShooter() instanceof Player) {
			Profile p = RpgPlugin.getInstance().getProfile((Player) event.getPotion().getShooter());
			if(p != null) {
				Character character = p.getCharacter();
				if(character == Characters.ADVENTURER) {
					// Adventurers potions last 2 time long.
					Potion potion = Potion.fromItemStack(event.getPotion().getItem());
					// Nice hack follows:
					event.setCancelled(true);
					// Potions in Minecraft have more then one effect?
					PotionEffect pe = potion.getEffects().iterator().next();
					for(LivingEntity e : event.getAffectedEntities()) {
						e.addPotionEffect(new PotionEffect(pe.getType(), pe.getDuration() * 2, pe.getAmplifier()));
					}
				}
			}
		}
	}
}
