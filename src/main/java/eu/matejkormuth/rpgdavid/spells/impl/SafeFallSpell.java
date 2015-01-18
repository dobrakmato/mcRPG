package eu.matejkormuth.rpgdavid.spells.impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.spells.Spell;

public class SafeFallSpell extends Spell {

    public SafeFallSpell() {
        super(Sound.WOLF_HOWL, "Safe fall spell", 70);
    }

    @Override
    protected void cast0(final Player invoker, Location location,
            Vector velocity) {
        // Reset falling damage.
        invoker.setFallDistance(0F);
        // Slow down player's fall.
        SafeFallRunnable runnable = new SafeFallRunnable(invoker);
        final int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(
                RpgPlugin.getInstance(), runnable, 0L, 1L);
        runnable.setTaskId(taskId);
    }

    private class SafeFallRunnable implements Runnable {
        private Player invoker;
        private float velocityY = 0.03F;
        private int taskId;

        public SafeFallRunnable(Player invoker) {
            this.invoker = invoker;
        }

        @Override
        public void run() {
            Vector velocity = invoker.getVelocity().add(
                    new Vector(0, velocityY, 0));

            if (velocity.getY() >= 0.1) {
                this.invoker.setFallDistance(0F);
                Bukkit.getScheduler().cancelTask(this.taskId);
            } else {
                this.invoker.setVelocity(velocity);
            }
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }
    }
}
