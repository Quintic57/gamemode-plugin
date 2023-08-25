package my.dw.gamemodeplugin.model;

import my.dw.gamemodeplugin.GameModePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

// TODO: Better way to implement this?
public abstract class GameModeHandler {

    protected final Scoreboard mainScoreboard;

    protected Player gmm; // GameMode Master, responsible for configuring the game mode. TODO: Is this needed?

    private BukkitRunnable timeOutRunnable;

    public GameModeHandler() {
        mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    }

    public abstract void startGame();

    protected void startGame(final GameMode gameMode) {
        final Objective mainObjective = mainScoreboard.registerNewObjective(gameMode.getMainCriterion().getKey(),
            gameMode.getMainCriterion().getValue(), gameMode.getMainCriterion().getKey());
        mainObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        //TODO: If numberOfTeams=0, display each player on scoreboard instead
        final GameModeConfiguration config = gameMode.getCurrentConfiguration();
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

        timeOutRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("The Timer has run out!");
                gameMode.getHandler().after();
            }
        };
        timeOutRunnable
            .runTaskLater(GameModePlugin.getPlugin(), config.getTimeLimitInMinutesConfig().getValue() * 1200)
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

    public void setGmm(final Player gmm) {
        this.gmm = gmm;
    }

}
