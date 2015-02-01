package eu.matejkormuth.rpgdavid.starving.zombie;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Location;

import eu.matejkormuth.rpgdavid.starving.Starving;

public class ZombiePool {
    private Queue<Zombie> inUse;
    private Queue<Zombie> available;

    private final Location poolLocation;
    private boolean shuttingDown = false;

    public ZombiePool(Location poolLocation, int capacity) {
        this.poolLocation = poolLocation;
        this.inUse = new LinkedList<Zombie>();
        this.available = new LinkedList<Zombie>();

        Starving.getInstance().getLogger()
                .info("Initializing ZombiePool with capacity of " + capacity);

        this.expand(capacity);
    }

    public Zombie acquire() {
        if (this.shuttingDown) {
            throw new UnsupportedOperationException(
                    "Can't acquire zombie while shutting down!");
        }

        if (this.available.isEmpty()) {
            // Expand pool size.
            this.expand(20);
        }
        Zombie z = this.available.poll();
        this.inUse.add(z);
        z.setDisabled(false);
        return z;
    }

    private void expand(int size) {
        for (int i = 0; i < size; i++) {
            Zombie z = new Zombie(this.poolLocation);
            z.setDisabled(true);
            this.available.add(z);
        }
    }

    public void release(final Zombie zombie) {
        if (this.shuttingDown) {
            throw new UnsupportedOperationException(
                    "Can't release zombie while shutting down!");
        }

        this.cleanUp(zombie);
        this.inUse.remove(zombie);
        this.available.add(zombie);
    }

    public void cleanUp(final Zombie zombie) {
        zombie.setFollowTarget(null);
        zombie.teleport(this.poolLocation);
        zombie.setDisabled(true);
    }

    public void shutdown() {
        this.shuttingDown = true;

        for (Iterator<Zombie> itr = this.available.iterator(); itr.hasNext();) {
            Zombie z = itr.next();
            z.destroy();
            itr.remove();
        }

        for (Iterator<Zombie> itr = this.inUse.iterator(); itr.hasNext();) {
            Zombie z = itr.next();
            z.destroy();
            itr.remove();
        }
    }
}
