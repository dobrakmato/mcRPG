package eu.matejkormuth.rpgdavid;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import eu.matejkormuth.rpgdavid.money.Currencies;

public class PlayerStatsScoreboard {
    private Scoreboard scoreboard;
    private Objective objective;
    private Profile profile;
    private Player player;

    private Score xpScore;
    private Score manaScore;
    private Score currency1Score;
    private Score currency2Score;

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
        this.currency1Score = this.objective.getScore(Currencies.NORMAL
                .getName());
        this.currency2Score = this.objective.getScore(Currencies.PREMIUM
                .getName());
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
        this.currency1Score.setScore(this.profile.getNormalMoney().getAmount());
        this.currency2Score
                .setScore(this.profile.getPremiumMoney().getAmount());
    }
}
