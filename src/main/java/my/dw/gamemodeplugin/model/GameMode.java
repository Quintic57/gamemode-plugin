package my.dw.gamemodeplugin.model;

import my.dw.gamemodeplugin.model.handlers.CaptureTheFlagHandler;
import my.dw.gamemodeplugin.model.handlers.DeathmatchHandler;
import my.dw.gamemodeplugin.model.handlers.DominationHandler;
import org.bukkit.scoreboard.Criteria;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum GameMode {

    CAPTURE_THE_FLAG(
        new CaptureTheFlagHandler(
            new GameModeConfiguration()
                .initNumberOfTeams(2, List.of(2, 3, 4, 5, 6))
                .initRandomizedTeams(false)
                .initScoreLimit(5, List.of(2, 4, 5, 6, 8, 10, 15, 20))
                .initTimeLimit(
                    Duration.of(15, ChronoUnit.MINUTES),
                    Stream.of(5, 10, 15, 20, 30, 40, 50, 60)
                        .map(numOfMins -> Duration.of(numOfMins, ChronoUnit.MINUTES))
                        .collect(Collectors.toList())
                )
                .initFoodEnabled(true)
        )
    ),
    DEATHMATCH(
        new DeathmatchHandler(
            new GameModeConfiguration()
                .initNumberOfTeams(2, List.of(0, 2, 3, 4, 5, 6, 7, 8))
                .initRandomizedTeams(false)
                .initScoreLimit(25, List.of(10, 15, 20, 25, 30, 35, 40, 50))
                .initTimeLimit(
                    Duration.of(10, ChronoUnit.MINUTES),
                    Stream.of(5, 10, 15, 20, 30, 40, 50, 60)
                        .map(numOfMins -> Duration.of(numOfMins, ChronoUnit.MINUTES))
                        .collect(Collectors.toList())
                )
                .initFoodEnabled(true)
        )
    ),
    DOMINATION(
        new DominationHandler(
            new GameModeConfiguration()
                .initNumberOfTeams(2, List.of(2, 3, 4, 5, 6))
                .initRandomizedTeams(false)
                .initScoreLimit(100, List.of(25, 50, 75, 100, 150, 200, 250, 300))
                .initTimeLimit(
                    Duration.of(20, ChronoUnit.MINUTES),
                    Stream.of(5, 10, 15, 20, 30, 40, 50, 60)
                        .map(numOfMins -> Duration.of(numOfMins, ChronoUnit.MINUTES))
                        .collect(Collectors.toList())
                )
                .initFoodEnabled(true)
        )
    );

    private final Map.Entry<String, Criteria> mainCriterion;

    private final Map<String, Criteria> additionalCriteria;

    private final GameModeHandler handler;

    GameMode(final GameModeHandler handler) {
        this(Map.entry("Score", Criteria.DUMMY), Map.of(), handler);
    }

    GameMode(final Map.Entry<String, Criteria> mainCriterion,
             final Map<String, Criteria> additionalCriteria,
             final GameModeHandler handler) {
        this.mainCriterion = mainCriterion;
        this.additionalCriteria = additionalCriteria;
        this.handler = handler;
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

}
