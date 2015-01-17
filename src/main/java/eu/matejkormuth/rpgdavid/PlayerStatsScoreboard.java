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
    
    private Score xpScore;
    private Score manaScore;
    
    public PlayerStatsScoreboard(final Player player) {
        this.profile = RpgPlugin.getInstance().getProfile(player);
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        
        this.objective = this.scoreboard.getObjective(profile.getCharacter().getName());
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        
        this.xpScore = this.objective.getScore("XP");
        this.manaScore = this.objective.getScore("Mana");
    }
    
    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }
    
    public void update() {
        this.xpScore.setScore((int) this.profile.getXp());
        this.manaScore.setScore(this.profile.getMana());
    }
}
