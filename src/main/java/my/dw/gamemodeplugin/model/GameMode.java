package my.dw.gamemodeplugin.model;

import my.dw.gamemodeplugin.model.handlers.CaptureTheFlagHandler;
import my.dw.gamemodeplugin.model.handlers.DeathmatchHandler;
import my.dw.gamemodeplugin.model.handlers.DominationHandler;
import org.bukkit.scoreboard.Criteria;

import java.util.Map;

public enum GameMode {

    CAPTURE_THE_FLAG(
        new CaptureTheFlagHandler(),
        GameModeConfiguration.CAPTURE_THE_FLAG,
        MapConfiguration.CAPTURE_THE_FLAG
    ),
    DEATHMATCH(
        new DeathmatchHandler(),
        GameModeConfiguration.DEATHMATCH,
        MapConfiguration.DEATHMATCH
    ),
    DOMINATION(
        new DominationHandler(),
        GameModeConfiguration.DOMINATION,
        MapConfiguration.DOMINATION
    );

    private final Map.Entry<String, Criteria> mainCriterion;

    private final Map<String, Criteria> additionalCriteria;

    private final GameModeHandler handler;

    private final GameModeConfiguration configuration;

    private final MapConfiguration mapConfiguration;

    GameMode(final GameModeHandler handler,
             final GameModeConfiguration configuration,
             final MapConfiguration mapConfiguration) {
        this(Map.entry("Score", Criteria.DUMMY), Map.of(), handler, configuration, mapConfiguration);
    }

    GameMode(final Map.Entry<String, Criteria> mainCriterion,
             final Map<String, Criteria> additionalCriteria,
             final GameModeHandler handler,
             final GameModeConfiguration configuration,
             final MapConfiguration mapConfiguration) {
        this.mainCriterion = mainCriterion;
        this.additionalCriteria = additionalCriteria;
        this.handler = handler;
        this.configuration = configuration;
        this.mapConfiguration = mapConfiguration;
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

    public GameModeConfiguration getConfiguration() {
        return configuration;
    }

    public MapConfiguration getMapConfiguration() {
        return mapConfiguration;
    }

}
