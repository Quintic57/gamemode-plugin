package my.dw.gamemodeplugin.model.handlers;

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

public class DeathmatchHandler extends GameModeHandler implements Listener {

    public DeathmatchHandler(final GameModeConfiguration defaultConfiguration) {
        super(defaultConfiguration);
    }

    @Override
    public void before(final Player gmm) {
        super.before(gmm);
        // Should probably use persistent data container or something
        GuiUtils.currentGameMode = GameMode.DEATHMATCH;
    }

    @Override
    public boolean startGame() {
        try {
            mainScoreboard.registerNewObjective(GameMode.DEATHMATCH.getMainCriterion().getKey(),
                GameMode.DEATHMATCH.getMainCriterion().getValue(), GameMode.DEATHMATCH.getMainCriterion().getKey());

            Bukkit.getServer().getPluginManager().registerEvents(this, GameModePlugin.getPlugin());

            //TODO: For testing purposes. Team building should not be done here, should be handled in the ConfigureGameModeGui
            final Team team1 = mainScoreboard.registerNewTeam("Team1");
            team1.setColor(ChatColor.RED);
            team1.addEntry("Quintic47");
            final Team team2 = mainScoreboard.registerNewTeam("Team2");
            team2.setColor(ChatColor.BLUE);
            team2.addEntry("konburuuu");

            return true;
        } catch (final Exception e) {
            Bukkit.getServer().getOnlinePlayers().forEach(player
                -> player.sendMessage("Exception thrown while starting game"));

            return false;
        }
    }

    @Override
    public void modifyScore(final Score currentScore) {
        currentScore.setScore(currentScore.getScore() + 1);
    }

    @Override
    public void after() {
        super.after();
        PlayerDeathEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onPlayerDeathEvent(final PlayerDeathEvent event) {
        final Player killer = event.getEntity().getKiller();
        if (killer == null) {
            return;
        }

        final Team team = mainScoreboard.getEntryTeam(killer.getName());
        final Score currentScore = mainScoreboard.getObjective(
            GameMode.DEATHMATCH.getMainCriterion().getKey()).getScore(team.getDisplayName());
        modifyScore(currentScore);
    }

}
