package eu.matejkormuth.rpgdavid.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Characters;
import eu.matejkormuth.rpgdavid.Dagger;
import eu.matejkormuth.rpgdavid.Profile;
import eu.matejkormuth.rpgdavid.RpgPlugin;

public class WeaponsListener implements Listener {
    // Dagger
    @EventHandler
    private void onDaggerHit(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Profile p = RpgPlugin.getInstance().getProfile(
                    (Player) event.getDamager());
            if (p != null) {
                Character character = p.getCharacter();
                // Only killer.
                if (character == Characters.KILLER) {
                    // Only damage by dagger.
                    if (Dagger.isDagger(((Player) event.getDamager())
                            .getItemInHand())) {
                        // Dagger does 12 HP damage.
                        event.setDamage(12D);
                    }
                }
            }
        }
    }
}
