package eu.matejkormuth.rpgdavid;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreboardsList extends ArrayList<PlayerStatsScoreboard> implements
        Runnable {
    private static final long serialVersionUID = 1L;

    @Override
    public boolean add(final PlayerStatsScoreboard e) {
        e.getPlayer().setScoreboard(e.getScoreboard());
        return super.add(e);
    }

    @Override
    public boolean remove(final Object o) {
        if (o instanceof PlayerStatsScoreboard) {
            ((PlayerStatsScoreboard) o).getPlayer().setScoreboard(
                    Bukkit.getScoreboardManager().getNewScoreboard());
        }
        return super.remove(o);
    }

    public void remove(final Player player) {
        for (PlayerStatsScoreboard p : this) {
            if (p.getPlayer().equals(player)) {
                this.remove(p);
                return;
            }
        }
    }

    @Override
    public void run() {
        for (PlayerStatsScoreboard p : this) {
            p.update();
        }
    }
}
