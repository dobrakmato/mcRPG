package eu.matejkormuth.rpgdavid.listeners;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Characters;
import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;

public class KnightListener implements Listener {
	private Random random = new Random();
	@EventHandler
	private void onDamageToKnight(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Profile p = RpgPlugin.getInstance().getProfile((Player) event.getEntity());
			if(p != null) {
				Character character = p.getCharacter();
				if(character == Characters.KNIGHT) {
					// Knight's armor have protection of breaking (implemented by chanche of giving durability back to armor).
					if(random.nextInt(3) == 0) { // 25%
						Player player = (Player)event.getEntity();
						player.getInventory().getHelmet().setDurability((short) (player.getInventory().getHelmet().getDurability() + 1));
						player.getInventory().getChestplate().setDurability((short) (player.getInventory().getChestplate().getDurability() + 1));
						player.getInventory().getLeggings().setDurability((short) (player.getInventory().getLeggings().getDurability() + 1));
						player.getInventory().getBoots().setDurability((short) (player.getInventory().getBoots().getDurability() + 1));
					}
				}
			}
		}
	}
}
