package eu.matejkormuth.rpgdavid.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class SpellsListener implements Listener {
    public static final double FIRE_SPELL_RADIUS = 5.0D;

    // Fire spell.
    @EventHandler
    private void onFireballHit(final ProjectileHitEvent event) {
        if (event.getEntity().hasMetadata("fireSpell")) {
            for (Entity e : event.getEntity().getNearbyEntities(
                    FIRE_SPELL_RADIUS, FIRE_SPELL_RADIUS, FIRE_SPELL_RADIUS)) {
                if(e instanceof LivingEntity) {
                    ((LivingEntity) e).damage(0.5D);
                    e.setFireTicks(20 * 10);
                }
            }
        }
    }
}
