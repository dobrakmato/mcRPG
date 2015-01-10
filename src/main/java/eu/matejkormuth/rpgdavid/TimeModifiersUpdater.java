package eu.matejkormuth.rpgdavid;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TimeModifiersUpdater implements Runnable {
	public void run() {
		Character character;
		for(Player p : Bukkit.getOnlinePlayers()) {
			Profile profile = RpgPlugin.getInstance().getProfile(p);
			if(profile != null) {
				if(profile.getCharacter() != null) {
					character = profile.getCharacter();
					
					// Update player's character.
					if(character == Characters.WEREWOLF) {
						updateWerewolf(p);
					} else if(character == Characters.VAMPIRE) {
						updateVampire(p);
					}
				}
			}
		}
	}

	private void updateVampire(Player p) {
		if(p.getWorld().getTime() > 14000) {
			// Vampires have night vision
			if(!p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 65 * 5, 0));
			}
			
			// Vampires are 2.3x faster in night.
			p.setWalkSpeed(p.getWalkSpeed() * 2.3F);
		} else {
			if(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
				p.removePotionEffect(PotionEffectType.NIGHT_VISION);
			}
			
			// Vampires get damaged on sunlight, when not wearing gold helmet.
			if(p.getInventory().getHelmet() == null || p.getInventory().getHelmet().getType() != Material.GOLD_HELMET) {
				p.damage(1D);
			}
			
			// Vampires are normal fast in day.
			p.setWalkSpeed(Characters.VAMPIRE.getModifiers().getWalkSpeedModifier() * 0.1F);
		}
	}

	private void updateWerewolf(Player p) {
		if(p.getWorld().getTime() > 14000) {
			if(!p.hasPotionEffect(PotionEffectType.SPEED)) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 65 * 5, 1));
			}
			
			if(!p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 65 * 5, 0));
			}
		} else {
			if(p.hasPotionEffect(PotionEffectType.SPEED)) {
				p.removePotionEffect(PotionEffectType.SPEED);
			}
			
			if(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
				p.removePotionEffect(PotionEffectType.NIGHT_VISION);
			}
		}
	}
}
