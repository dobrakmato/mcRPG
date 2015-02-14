package eu.matejkormuth.rpgdavid.starving;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.starving.persistence.Persist;
import eu.matejkormuth.rpgdavid.starving.persistence.PersistInjector;

public class Data {
    private static final Map<Player, Data> cached;

    static {
        cached = new HashMap<>();
    }

    private static File dataFileOf(Player player) {
        return RpgPlugin.getInstance().getFile("pdatas",
                player.getUniqueId().toString() + ".data");
    }

    public static Data of(Player player) {
        if (cached.containsKey(player)) {
            return cached.get(player);
        } else {
            File f = null;
            if ((f = dataFileOf(player)).exists()) {
                return new Data(f);
            } else {
                return new Data(player);
            }
        }
    }

    private Player player;

    @Persist(key = "bleedingTicks")
    private int bleedingTicks = 0;

    private Data(Player player) {
        this.player = player;
    }

    public Data(File f) {
        PersistInjector.inject(this, f);
    }

    public Data save() {
        PersistInjector.store(this, dataFileOf(this.player));
        return this;
    }

    public Data uncache() {
        cached.remove(this.player);
        return this;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getBleedingTicks() {
        return this.bleedingTicks;
    }

    public void setBleedingTicks(int bleedingTicks) {
        this.bleedingTicks = bleedingTicks;
    }
}
