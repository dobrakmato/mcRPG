package eu.matejkormuth.rpgdavid.starving.achievements.death;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import eu.matejkormuth.rpgdavid.starving.achievements.Achievement;

public class Atlantis extends Achievement {

    public Atlantis() {
        super("Atlantis", "Suffocate in water 10 times.", 10);
    }

    @EventHandler
    private void onPlayerKilled(final EntityDamageEvent event) {
        // Check only players.
        if (event.getEntity() instanceof Player) {
            // Check if entity dies in this hit.
            if (((Player) event).getHealth() - event.getFinalDamage() <= 0) {

                // Check for specific way of death.
                if (event.getCause() == DamageCause.DROWNING) {
                    this.progress((Player) event.getEntity(), 1);
                }
            }
        }
    }

}
