package my.dw.gamemodeplugin.model;

import my.dw.gamemodeplugin.model.gamemodes.CaptureTheFlagHandler;
import my.dw.gamemodeplugin.model.gamemodes.DeathmatchHandler;
import my.dw.gamemodeplugin.model.gamemodes.DominationHandler;
import org.bukkit.scoreboard.Criteria;

import java.util.Map;

public enum GameMode {

    CAPTURE_THE_FLAG(new CaptureTheFlagHandler()),
    DEATHMATCH(new DeathmatchHandler()),
    DOMINATION(new DominationHandler());

    private final Map.Entry<String, Criteria> mainCriterion;

    private final Map<String, Criteria> additionalCriteria;

    private final GameModeConfiguration currentConfiguration;

    private final GameModeHandler handler;

    GameMode(final GameModeHandler handler) {
        this(Map.entry("Score", Criteria.DUMMY), Map.of(), handler);
    }

    GameMode(final Map.Entry<String, Criteria> mainCriterion,
             final Map<String, Criteria> additionalCriteria,
             final GameModeHandler handler) {
        this.mainCriterion = mainCriterion;
        this.additionalCriteria = additionalCriteria;
        this.currentConfiguration = new GameModeConfiguration();
        this.handler = handler;
    }

    public Map.Entry<String, Criteria> getMainCriterion() {
        return mainCriterion;
    }

    public Map<String, Criteria> getAdditionalCriteria() {
        return additionalCriteria;
    }

    public GameModeConfiguration getCurrentConfiguration() {
        return currentConfiguration;
    }

    public GameModeHandler getHandler() {
        return handler;
    }

}
