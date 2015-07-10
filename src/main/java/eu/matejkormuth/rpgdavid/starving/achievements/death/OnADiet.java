package eu.matejkormuth.rpgdavid.starving.achievements.death;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import eu.matejkormuth.rpgdavid.starving.achievements.Achievement;

public class OnADiet extends Achievement {

    public OnADiet() {
        super("On a Diet", "Sometime it's just better to eat.");
    }

    @EventHandler
    private void onPlayerKilled(final EntityDamageEvent event) {
        // Check only players.
        if (event.getEntity() instanceof Player) {
            // Check if entity dies in this hit.
            if (((Player) event).getHealth() - event.getFinalDamage() <= 0) {

                // Check for specific way of death.
                if (event.getCause() == DamageCause.STARVATION) {
                    this.complete((Player) event.getEntity());
                }
            }
        }
    }

}
