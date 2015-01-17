package eu.matejkormuth.rpgdavid;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ManaUpdater implements Runnable {
    private RpgPlugin plugin = RpgPlugin.getInstance();
    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            this.plugin.getProfile(p).giveMana(1);
        }
    }
}
