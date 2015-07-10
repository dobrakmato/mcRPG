package eu.matejkormuth.rpgdavid.starving.achievements.death;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import eu.matejkormuth.rpgdavid.starving.achievements.Achievement;

public class Hades extends Achievement {

    public Hades() {
        super("Hades", "Die 100 times.", 100);
    }

    @EventHandler
    private void onPlayerKilled(final EntityDamageEvent event) {
        // Check only players.
        if (event.getEntity() instanceof Player) {
            // Check if entity dies in this hit.
            if (((Player) event).getHealth() - event.getFinalDamage() <= 0) {
                this.progress((Player) event.getEntity(), 1);
            }
        }
    }

}
