package my.dw.gamemodeplugin.model.gamemodes;

import my.dw.gamemodeplugin.GameModePlugin;
import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.model.GameModeConfiguration;
import my.dw.gamemodeplugin.model.GameModeHandler;
import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

public class DeathmatchHandler extends GameModeHandler implements Listener {

    @Override
    public void before() {
        super.before();

        Bukkit.getServer().getPluginManager().registerEvents(this, GameModePlugin.getPlugin());
        // Should probably use persistent data container or something
        GuiUtils.currentGameMode = GameMode.DEATHMATCH;
    }

    @Override
    public void modifyScore(final Score currentScore) {
        currentScore.setScore(currentScore.getScore() + 1);
//        mainScoreboard.getEntryTeam(currentScore.getEntry());
    }

    @Override
    public void after() {
        super.after();

        PlayerDeathEvent.getHandlerList().unregister(this);
    }

    @Override
    public GameModeConfiguration getDefaultConfiguration() {
        return GameModeConfiguration.builder()
            .numberOfTeams(2)
            .teamCaptainToPlayerList(new HashMap<>())
            .isFoodEnabled(true)
            .timeLimit(Duration.of(15, ChronoUnit.MINUTES))
            .isConfigured(false)
            .build();
    }

    @EventHandler
    public void onPlayerDeathEvent(final PlayerDeathEvent event) {
        final Player killer = event.getEntity().getKiller();
        final Team team = mainScoreboard.getEntryTeam(killer.getName()); //TODO: confirm this
        final Score currentScore = mainScoreboard.getObjective(
            GameMode.DEATHMATCH.getMainCriterion().getKey()).getScore(team.getDisplayName());
        modifyScore(currentScore);
    }

}
