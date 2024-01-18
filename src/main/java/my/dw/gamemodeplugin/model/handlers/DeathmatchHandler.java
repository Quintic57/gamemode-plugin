package my.dw.gamemodeplugin.model.handlers;

import my.dw.gamemodeplugin.GameModePlugin;
import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.model.GameModeHandler;
import my.dw.gamemodeplugin.utils.GameModeUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

public class DeathmatchHandler extends GameModeHandler implements Listener {

    public DeathmatchHandler() {
        super();
    }

    @Override
    public void before(final Player gmm) {
        super.before(gmm);
    }

    @Override
    public void startGame(final Player player) {
        Bukkit.getServer().getPluginManager().registerEvents(this, GameModePlugin.getPlugin());
        player.getWorld()
            .getPersistentDataContainer()
            .set(GameModeUtils.CURRENT_GAME_MODE_KEY, PersistentDataType.STRING, GameMode.DEATHMATCH.name());
        startGame(GameMode.DEATHMATCH);
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

        // TODO: Handle cases for free-for-all games
        final Team team = getMainScoreboard().getEntryTeam(killer.getName());
        final Score currentScore = getMainScoreboard().getObjective(
            GameMode.DEATHMATCH.getMainCriterion().getKey()).getScore(team.getColor() + team.getDisplayName());
        modifyScore(currentScore);
    }

}
