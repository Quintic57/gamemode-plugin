package my.dw.gamemodeplugin.model;

import my.dw.gamemodeplugin.GameModePlugin;
import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public abstract class GameModeHandler {

    protected final Scoreboard mainScoreboard;

    public GameModeHandler() {
        this.mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    }

    public void before() {
        mainScoreboard.registerNewObjective(GameMode.DEATHMATCH.getMainCriterion().getKey(),
            GameMode.DEATHMATCH.getMainCriterion().getValue(), GameMode.DEATHMATCH.getMainCriterion().getKey());
        //TODO: replace with team selector
        final Team temp = mainScoreboard.registerNewTeam("Team1");
        temp.setColor(ChatColor.RED);

//        additionalCriteria.keySet()
//            .forEach(c -> {
//                final Objective o = scoreboard.registerNewObjective(c, additionalCriteria.get(c), c);
//                ALL_OBJECTIVES.add(o);
//            });
    }

    public boolean startGame() {
        return true;
    }

    public abstract void modifyScore(final Score currentScore);

    public void after() {
        mainScoreboard.getTeams().forEach(Team::unregister);
        mainScoreboard.getObjectives().forEach(Objective::unregister);
        GuiUtils.currentGameMode = null;
    }

    public abstract GameModeConfiguration getDefaultConfiguration();

}
