package my.dw.gamemodeplugin.model;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum GameMode {
    DEATHMATCH(),
    DOMINATION();

    private final Map.Entry<String, Criteria> mainCriterion;

    private final Map<String, Criteria> additionalCriteria;

    public static final Set<Objective> ALL_OBJECTIVES;

    static {
        ALL_OBJECTIVES = new HashSet<>();
    }

    GameMode() {
        this(Map.entry("Score", Criteria.TRIGGER), Map.of());
    }

    GameMode(final Map.Entry<String, Criteria> mainCriterion, final Map<String, Criteria> additionalCriteria) {
        this.mainCriterion = mainCriterion;
        this.additionalCriteria = additionalCriteria;
    }
    
    public void before(final Player player) {
        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        // TODO: Add a team selector here, basically build a GUI that has clickable skulls for each online player,
        //  select team captains first, then alternate between when picking the rest of the players.
        final boolean success = selectTeams(player);
        if (!success) {
            return;
        }

        final Objective mainObjective = scoreboard.registerNewObjective(mainCriterion.getKey(),
            mainCriterion.getValue(), mainCriterion.getKey());
        mainObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        additionalCriteria.keySet()
            .forEach(c -> {
                final Objective o = scoreboard.registerNewObjective(c, additionalCriteria.get(c), c);
                ALL_OBJECTIVES.add(o);
            });

        Bukkit.getOnlinePlayers().forEach(p -> {
            p.setScoreboard(scoreboard);
        });
    }

    public static boolean selectTeams(final Player player) {
        return true;
    }

    public static void after() {
        ALL_OBJECTIVES.forEach(Objective::unregister);
        ALL_OBJECTIVES.clear();
    }

}
