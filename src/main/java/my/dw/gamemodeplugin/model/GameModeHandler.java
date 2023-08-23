package my.dw.gamemodeplugin.model;

import my.dw.gamemodeplugin.GameModePlugin;
import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public abstract class GameModeHandler {

    protected final Scoreboard mainScoreboard;

    protected Player gmm; // GameMode Master, responsible for configuring the game mode. TODO: Is this needed?

    private final BukkitRunnable timeOutTask;

    public GameModeHandler() {
        mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        timeOutTask = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player ->
                    player.sendMessage("The Timer has run out!"));
            }
        };
    }

    public boolean startGame() {
        if (GuiUtils.currentGameMode == null) {
            gmm.sendMessage("No GameMode is currently selected");
            return false;
        }

        try {
            mainScoreboard.registerNewObjective(GuiUtils.currentGameMode.getMainCriterion().getKey(),
                GuiUtils.currentGameMode.getMainCriterion().getValue(), GuiUtils.currentGameMode.getMainCriterion().getKey());

            final GameModeConfiguration config = GuiUtils.currentGameMode.getCurrentConfiguration();
            for (final GameModeTeam teamConfig: config.getTeams()) {
                final Team team = mainScoreboard.registerNewTeam(teamConfig.getName());
                team.setColor(teamConfig.getColor());
                team.addEntry(Bukkit.getPlayer(teamConfig.getCaptain()).getDisplayName());
                teamConfig.getPlayerList()
                    .stream()
                    .map(playerUUID -> Bukkit.getPlayer(playerUUID))
                    .forEach(player -> {
                        team.addEntry(player.getDisplayName());
                    });
            }

            timeOutTask.runTaskLater(GameModePlugin.getPlugin(), config.getTimeLimitInMinutesConfig().getValue() * 1200);

            return true;
        } catch (final Exception e) {
            Bukkit.getServer().getOnlinePlayers().forEach(player
                -> player.sendMessage("Exception thrown while starting game"));

            return false;
        }

    }

    public abstract void modifyScore(final Score currentScore);

    public void before(final Player gmm) {
        setGmm(gmm);
    }

    public void after() {
        setGmm(null);
        timeOutTask.cancel();
        mainScoreboard.getTeams().forEach(Team::unregister);
        mainScoreboard.getObjectives().forEach(Objective::unregister);
        GuiUtils.currentGameMode = null;
    }

    public void setGmm(final Player gmm) {
        this.gmm = gmm;
    }

}
