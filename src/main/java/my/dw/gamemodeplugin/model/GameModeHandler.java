package my.dw.gamemodeplugin.model;

import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public abstract class GameModeHandler {

    protected final Scoreboard mainScoreboard;

    protected final GameModeConfiguration currentConfiguration;

    protected Player gmm; // GameMode Master, responsible for configuring the game mode

    public GameModeHandler(final GameModeConfiguration defaultConfiguration) {
        currentConfiguration = defaultConfiguration;
        mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    }

    public abstract boolean startGame();

    public abstract void modifyScore(final Score currentScore);

    public void before(final Player gmm) {
        setGmm(gmm);
    }

    public void after() {
        setGmm(null);
        mainScoreboard.getTeams().forEach(Team::unregister);
        mainScoreboard.getObjectives().forEach(Objective::unregister);
        GuiUtils.currentGameMode = null;
    }

    public GameModeConfiguration getCurrentConfiguration() {
        return currentConfiguration;
    }

    public void setGmm(final Player gmm) {
        this.gmm = gmm;
    }

}
