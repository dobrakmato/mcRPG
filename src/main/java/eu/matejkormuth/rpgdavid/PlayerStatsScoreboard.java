package eu.matejkormuth.rpgdavid;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerStatsScoreboard {
    private Scoreboard scoreboard;
    private Objective objective;
    private Profile profile;
    private Player player;

    private Score xpScore;
    private Score manaScore;
    private Score florinsScore;
    private Score dollarsScore;

    public PlayerStatsScoreboard(final Player player) {
        this.player = player;
        this.profile = RpgPlugin.getInstance().getProfile(player);
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        this.objective = this.scoreboard.registerNewObjective("sidebar_main",
                "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.objective.setDisplayName(this.profile.getCharacter().getName());

        this.xpScore = this.objective.getScore("XP");
        this.manaScore = this.objective.getScore("Mana");
        this.florinsScore = this.objective.getScore("Florins");
        this.dollarsScore = this.objective.getScore("Dollars");
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public Player getPlayer() {
        return player;
    }

    public void update() {
        this.xpScore.setScore((int) this.profile.getXp());
        this.manaScore.setScore(this.profile.getMana());
        this.florinsScore.setScore(this.profile.getFlorins());
        this.dollarsScore.setScore(this.profile.getDollars());
    }
}
