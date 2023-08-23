package my.dw.gamemodeplugin.model;

import my.dw.gamemodeplugin.model.handlers.CaptureTheFlagHandler;
import my.dw.gamemodeplugin.model.handlers.DeathmatchHandler;
import my.dw.gamemodeplugin.model.handlers.DominationHandler;
import org.bukkit.scoreboard.Criteria;

import java.util.List;
import java.util.Map;

public enum GameMode {

    CAPTURE_THE_FLAG(
        new CaptureTheFlagHandler(),
        new GameModeConfiguration()
            .initNumberOfTeamsConfig(2, List.of(2, 3, 4, 5, 6))
            .initRandomizedTeamsConfig(false)
            .initScoreLimitConfig(5, List.of(2, 4, 5, 6, 8, 10, 15, 20))
            .initTimeLimitInMinutesConfig(15, List.of(5, 10, 15, 20, 30, 40, 50, 60))
            .initFoodEnabledConfig(true)
            .initTeamsList()
    ),
    DEATHMATCH(
        new DeathmatchHandler(),
        new GameModeConfiguration()
            .initNumberOfTeamsConfig(2, List.of(0, 2, 3, 4, 5, 6, 7, 8))
            .initRandomizedTeamsConfig(false)
            .initScoreLimitConfig(25, List.of(10, 15, 20, 25, 30, 35, 40, 50))
            .initTimeLimitInMinutesConfig(15, List.of(5, 10, 15, 20, 30, 40, 50, 60))
            .initFoodEnabledConfig(true)
            .initTeamsList()
    ),
    DOMINATION(
        new DominationHandler(),
        new GameModeConfiguration()
            .initNumberOfTeamsConfig(2, List.of(2, 3, 4, 5, 6))
            .initRandomizedTeamsConfig(false)
            .initScoreLimitConfig(100, List.of(25, 50, 75, 100, 150, 200, 250, 300))
            .initTimeLimitInMinutesConfig(15, List.of(5, 10, 15, 20, 30, 40, 50, 60))
            .initFoodEnabledConfig(true)
            .initTeamsList()
    );

    private final Map.Entry<String, Criteria> mainCriterion;

    private final Map<String, Criteria> additionalCriteria;

    private final GameModeHandler handler;

    private final GameModeConfiguration currentConfiguration;

    GameMode(final GameModeHandler handler, final GameModeConfiguration currentConfiguration) {
        this(Map.entry("Score", Criteria.DUMMY), Map.of(), handler, currentConfiguration);
    }

    GameMode(final Map.Entry<String, Criteria> mainCriterion,
             final Map<String, Criteria> additionalCriteria,
             final GameModeHandler handler,
             final GameModeConfiguration currentConfiguration) {
        this.mainCriterion = mainCriterion;
        this.additionalCriteria = additionalCriteria;
        this.handler = handler;
        this.currentConfiguration = currentConfiguration;
    }

    public Map.Entry<String, Criteria> getMainCriterion() {
        return mainCriterion;
    }

    public Map<String, Criteria> getAdditionalCriteria() {
        return additionalCriteria;
    }

    public GameModeHandler getHandler() {
        return handler;
    }

    public GameModeConfiguration getCurrentConfiguration() {
        return currentConfiguration;
    }

}
