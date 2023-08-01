package my.dw.gamemodeplugin.model;


import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class GameModeConfiguration {

    private final ConfigurationValue<Integer> numberOfTeams;

    private final ConfigurationValue<Boolean> randomizedTeams;

    private final ConfigurationValue<Integer> scoreLimit;

    private final ConfigurationValue<Duration> timeLimit;

    private final ConfigurationValue<Boolean> foodEnabled;

    private final Map<UUID, Set<UUID>> teamCaptainToPlayerList;

    // TODO: Should probably snapshot (OPPA) the online player list when initiating select game mode, that way there's
    //  something to reset to if players all agree to reset the team selection
    public GameModeConfiguration() {
        numberOfTeams = new ConfigurationValue<>();
        randomizedTeams = new ConfigurationValue<>();
        scoreLimit = new ConfigurationValue<>();
        timeLimit = new ConfigurationValue<>();
        foodEnabled = new ConfigurationValue<>();
        teamCaptainToPlayerList = new HashMap<>();
    }

    public GameModeConfiguration initNumberOfTeams(final int defaultValue, final List<Integer> valueRange) {
        this.numberOfTeams.setData(defaultValue);
        this.numberOfTeams.setDefaultValue(defaultValue);
        this.numberOfTeams.setValueRange(valueRange);
        return this;
    }
    
    public GameModeConfiguration initRandomizedTeams(final boolean defaultValue) {
        this.randomizedTeams.setData(defaultValue);
        this.randomizedTeams.setDefaultValue(defaultValue);
        this.randomizedTeams.setValueRange(List.of(false, true));
        return this;
    }
    
    public GameModeConfiguration initScoreLimit(final int defaultValue, final List<Integer> valueRange) {
        this.scoreLimit.setData(defaultValue);
        this.scoreLimit.setDefaultValue(defaultValue);
        this.scoreLimit.setValueRange(valueRange);
        return this;
    }

    public GameModeConfiguration initTimeLimit(final Duration defaultValue, final List<Duration> valueRange) {
        this.timeLimit.setData(defaultValue);
        this.timeLimit.setDefaultValue(defaultValue);
        this.timeLimit.setValueRange(valueRange);
        return this;
    }

    public GameModeConfiguration initFoodEnabled(final boolean defaultValue) {
        this.foodEnabled.setData(defaultValue);
        this.foodEnabled.setDefaultValue(defaultValue);
        this.foodEnabled.setValueRange(List.of(false, true));
        return this;
    }

    public ConfigurationValue<Integer> getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(final int numberOfTeams) {
        this.numberOfTeams.setData(numberOfTeams);
    }

    public ConfigurationValue<Boolean> getRandomizedTeams() {
        return randomizedTeams;
    }

    public void setRandomizedTeams(final boolean randomizedTeams) {
        this.randomizedTeams.setData(randomizedTeams);
    }

    public ConfigurationValue<Integer> getScoreLimit() {
        return scoreLimit;
    }

    public void setScoreLimit(final int scoreLimit) {
        this.scoreLimit.setData(scoreLimit);
    }

    public ConfigurationValue<Duration> getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(final Duration timeLimit) {
        this.timeLimit.setData(timeLimit);
    }

    public ConfigurationValue<Boolean> getFoodEnabled() {
        return foodEnabled;
    }

    public void setFoodEnabled(final boolean foodEnabled) {
        this.foodEnabled.setData(foodEnabled);
    }

    public Map<UUID, Set<UUID>> getTeamCaptainToPlayerList() {
        return teamCaptainToPlayerList;
    }

    public void reset() {
        numberOfTeams.setData(numberOfTeams.getDefaultValue());
        randomizedTeams.setData(randomizedTeams.getDefaultValue());
        scoreLimit.setData(scoreLimit.getDefaultValue());
        timeLimit.setData(timeLimit.getDefaultValue());
        foodEnabled.setData(foodEnabled.getDefaultValue());
        teamCaptainToPlayerList.clear();
    }
    
}
