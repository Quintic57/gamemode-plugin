package my.dw.gamemodeplugin.model;

import my.dw.gamemodeplugin.GameModePlugin;
import my.dw.gamemodeplugin.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public abstract class GameModeHandler {

    private final Scoreboard mainScoreboard;
    private Player gmm; // GameMode Master, responsible for configuring the game mode. TODO: Is this needed?

    public GameModeHandler() {
        mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    }

    protected Scoreboard getMainScoreboard() {
        return mainScoreboard;
    }

    protected Player getGmm() {
        return gmm;
    }

    protected void setGmm(final Player gmm) {
        this.gmm = gmm;
    }

    public abstract void startGame(final Player player);

    protected void startGame(final GameMode gameMode) {
        final Objective mainObjective = mainScoreboard.registerNewObjective(gameMode.getMainCriterion().getKey(),
            gameMode.getMainCriterion().getValue(), gameMode.getMainCriterion().getKey());
        mainObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        //TODO: If numberOfTeams=0, display each player on scoreboard instead
        final GameModeConfiguration config = gameMode.getConfiguration();
        for (final GameModeTeam teamConfig: config.getTeams()) {
            final Team team = mainScoreboard.registerNewTeam(teamConfig.getName());
            team.setColor(teamConfig.getColor());
            team.addEntry(Bukkit.getPlayer(teamConfig.getCaptain()).getDisplayName());
            teamConfig.getPlayerList()
                .stream()
                .map(Bukkit::getPlayer)
                .forEach(player -> {
                    team.addEntry(player.getDisplayName());
                });
            mainObjective.getScore(team.getColor() + team.getDisplayName()).setScore(0);
        }

        final BukkitRunnable timeOutRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("The Timer has run out!");
                gameMode.getHandler().after();
            }
        };
        timeOutRunnable
            .runTaskLater(GameModePlugin.getPlugin(),
                config.getTimeLimitInMinutesConfig().getCurrentValue() * 60L * Constants.TICKS_PER_SECOND)
            .getTaskId();
    }

    public abstract void modifyScore(final Score currentScore);

    public void before(final Player gmm) {
        setGmm(gmm);
    }

    public void after() {
        setGmm(null);

        Bukkit.getScheduler().cancelTasks(GameModePlugin.getPlugin());
        mainScoreboard.getTeams().forEach(Team::unregister);
        mainScoreboard.getObjectives().forEach(Objective::unregister);
    }

}
