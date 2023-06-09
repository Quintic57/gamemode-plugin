package my.dw.gamemodeplugin.model;

import lombok.Builder;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Builder
public class GameModeConfiguration {

    // Configuration happens before team selection. IF this is default value, configuration hasn't happened yet,
    // meaning we can cancel the click event and inform the player
    private int numberOfTeams;

    private Map<UUID, Set<UUID>> teamCaptainToPlayerList;

    private boolean isFoodEnabled;

    private Duration timeLimit;

    private boolean isConfigured;

    public GameModeConfiguration() {
        resetConfiguration();
    }

    public void resetConfiguration() {
        this.numberOfTeams = 0;
        this.teamCaptainToPlayerList = new HashMap<>();
        this.isFoodEnabled = true;
        this.timeLimit = Duration.of(30L, ChronoUnit.MINUTES);
        this.isConfigured = false;
    }

    public void setNumberOfTeams(final int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public void setFoodEnabled(final boolean foodEnabled) {
        isFoodEnabled = foodEnabled;
    }

    public void setTimeLimit(final Duration timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void setConfigured(final boolean configured) {
        isConfigured = configured;
    }

}
