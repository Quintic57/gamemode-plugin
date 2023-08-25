package my.dw.gamemodeplugin.model;


import lombok.Getter;
import my.dw.gamemodeplugin.utils.GameModeUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
public class GameModeConfiguration {

    private final ConfigurationValue<Integer> numberOfTeamsConfig;

    private final ConfigurationValue<Boolean> randomizedTeamsConfig;

    private final ConfigurationValue<Integer> scoreLimitConfig;

    private final ConfigurationValue<Integer> timeLimitInMinutesConfig;

    private final ConfigurationValue<Boolean> foodEnabledConfig;

    private final List<GameModeTeam> teams;

    // TODO: Should probably snapshot (OPPA) the online player list when initiating select game mode, that way there's
    //  something to reset to if players all agree to reset the team selection
    public GameModeConfiguration() {
        numberOfTeamsConfig = new ConfigurationValue<>();
        randomizedTeamsConfig = new ConfigurationValue<>();
        scoreLimitConfig = new ConfigurationValue<>();
        timeLimitInMinutesConfig = new ConfigurationValue<>();
        foodEnabledConfig = new ConfigurationValue<>();
        teams = new ArrayList<>();
    }

    public GameModeConfiguration initNumberOfTeamsConfig(final int defaultValue, final List<Integer> valueRange) {
        this.numberOfTeamsConfig.setValue(defaultValue);
        this.numberOfTeamsConfig.setDefaultValue(defaultValue);
        this.numberOfTeamsConfig.setValueRange(valueRange);
        return this;
    }
    
    public GameModeConfiguration initRandomizedTeamsConfig(final boolean defaultValue) {
        this.randomizedTeamsConfig.setValue(defaultValue);
        this.randomizedTeamsConfig.setDefaultValue(defaultValue);
        this.randomizedTeamsConfig.setValueRange(List.of(false, true));
        return this;
    }
    
    public GameModeConfiguration initScoreLimitConfig(final int defaultValue, final List<Integer> valueRange) {
        this.scoreLimitConfig.setValue(defaultValue);
        this.scoreLimitConfig.setDefaultValue(defaultValue);
        this.scoreLimitConfig.setValueRange(valueRange);
        return this;
    }

    public GameModeConfiguration initTimeLimitInMinutesConfig(final Integer defaultValue,
                                                              final List<Integer> valueRange) {
        this.timeLimitInMinutesConfig.setValue(defaultValue);
        this.timeLimitInMinutesConfig.setDefaultValue(defaultValue);
        this.timeLimitInMinutesConfig.setValueRange(valueRange);
        return this;
    }

    public GameModeConfiguration initFoodEnabledConfig(final boolean defaultValue) {
        this.foodEnabledConfig.setValue(defaultValue);
        this.foodEnabledConfig.setDefaultValue(defaultValue);
        this.foodEnabledConfig.setValueRange(List.of(false, true));
        return this;
    }

    public GameModeConfiguration initTeamsList() {
        registerNewTeams(numberOfTeamsConfig.getValue());
        return this;
    }

    public void registerNewTeams(final int numberOfTeams) {
        teams.clear();
        final Random random = new Random();
        final List<ChatColor> randomColors = random
            .ints(numberOfTeams, 0, GameModeUtils.MAX_NUMBER_OF_TEAMS)
            .mapToObj(GameModeUtils.TEAM_COLOR_OPTIONS::get)
            .collect(Collectors.toList());
        for (int i = 0; i < numberOfTeams; i++) {
            teams.add(new GameModeTeam("Team " + (i + 1), randomColors.get(i)));
        }
    }

    public void resetTeams() {
        teams.forEach(GameModeTeam::reset);
    }

    public void setNumberOfTeamsValue(final int numberOfTeamsConfig) {
        this.numberOfTeamsConfig.setValue(numberOfTeamsConfig);
    }

    public void setRandomizedTeamsValue(final boolean randomizedTeamsConfig) {
        this.randomizedTeamsConfig.setValue(randomizedTeamsConfig);
    }

    public void setScoreLimitValue(final int scoreLimitConfig) {
        this.scoreLimitConfig.setValue(scoreLimitConfig);
    }

    public void setTimeLimitInMinutesValue(final Integer timeLimitInMinutesConfig) {
        this.timeLimitInMinutesConfig.setValue(timeLimitInMinutesConfig);
    }

    public void setFoodEnabledValue(final boolean foodEnabledConfig) {
        this.foodEnabledConfig.setValue(foodEnabledConfig);
    }

    public void reset() {
        numberOfTeamsConfig.setValue(numberOfTeamsConfig.getDefaultValue());
        randomizedTeamsConfig.setValue(randomizedTeamsConfig.getDefaultValue());
        scoreLimitConfig.setValue(scoreLimitConfig.getDefaultValue());
        timeLimitInMinutesConfig.setValue(timeLimitInMinutesConfig.getDefaultValue());
        foodEnabledConfig.setValue(foodEnabledConfig.getDefaultValue());
        registerNewTeams(numberOfTeamsConfig.getDefaultValue());
    }
    
}
