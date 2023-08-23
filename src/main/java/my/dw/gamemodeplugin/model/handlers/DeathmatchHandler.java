package my.dw.gamemodeplugin.model.handlers;

import my.dw.gamemodeplugin.GameModePlugin;
import my.dw.gamemodeplugin.model.GameMode;
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

    @Override
    public void before(final Player gmm) {
        super.before(gmm);
        // Should probably use persistent data container or something
        GuiUtils.currentGameMode = GameMode.DEATHMATCH;
    }

    @Override
    public boolean startGame() {
        Bukkit.getServer().getPluginManager().registerEvents(this, GameModePlugin.getPlugin());
        return super.startGame();
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
