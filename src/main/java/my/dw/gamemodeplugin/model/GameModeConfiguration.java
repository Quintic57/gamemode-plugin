package my.dw.gamemodeplugin.model;


import lombok.Getter;
import my.dw.gamemodeplugin.utils.GameModeUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
public enum GameModeConfiguration {

    CAPTURE_THE_FLAG(
        new GameModeConfigurationElement<>("Number of Teams", 2, List.of(2, 3, 4, 5, 6)),
        new GameModeConfigurationElement<>("Randomized Teams", false, List.of(false, true)),
        new GameModeConfigurationElement<>("Score Limit", 5, List.of(2, 4, 5, 6, 8, 10, 15, 20)),
        new GameModeConfigurationElement<>("Time Limit (Minutes)", 15, List.of(5, 10, 15, 20, 30, 40, 50, 60)),
        new GameModeConfigurationElement<>("Food Enabled", true, List.of(false, true))
    ),
    DEATHMATCH(
        new GameModeConfigurationElement<>("Number of Teams", 2, List.of(0, 2, 3, 4, 5, 6, 7, 8)),
        new GameModeConfigurationElement<>("Randomized Teams", false, List.of(false, true)),
        new GameModeConfigurationElement<>("Score Limit", 25, List.of(10, 15, 20, 25, 30, 35, 40, 50)),
        new GameModeConfigurationElement<>("Time Limit (Minutes)", 15, List.of(5, 10, 15, 20, 30, 40, 50, 60)),
        new GameModeConfigurationElement<>("Food Enabled", true, List.of(false, true))
    ),
    DOMINATION(
        new GameModeConfigurationElement<>("Number of Teams", 2, List.of(2, 3, 4, 5, 6)),
        new GameModeConfigurationElement<>("Randomized Teams", false, List.of(false, true)),
        new GameModeConfigurationElement<>("Score Limit", 100, List.of(25, 50, 75, 100, 150, 200, 250, 300)),
        new GameModeConfigurationElement<>("Time Limit (Minutes)", 15, List.of(5, 10, 15, 20, 30, 40, 50, 60)),
        new GameModeConfigurationElement<>("Food Enabled", true, List.of(false, true)),
        List.of(
            new GameModeConfigurationElement<>("Number of Landmarks", 5, List.of(3, 4, 5, 6, 7, 8, 9, 10))
        )
    );

    private final GameModeConfigurationElement<Integer> numberOfTeamsConfig;
    private final GameModeConfigurationElement<Boolean> randomizedTeamsConfig;
    private final GameModeConfigurationElement<Integer> scoreLimitConfig;
    private final GameModeConfigurationElement<Integer> timeLimitInMinutesConfig;
    private final GameModeConfigurationElement<Boolean> foodEnabledConfig;
    private final List<GameModeTeam> teams;
    private final Map<String, GameModeConfigurationElement<?>> mandatoryConfigMap;
    private final Map<String, GameModeConfigurationElement<Integer>> customConfigMap;

    GameModeConfiguration(final GameModeConfigurationElement<Integer> numberOfTeamsConfig,
                          final GameModeConfigurationElement<Boolean> randomizedTeamsConfig,
                          final GameModeConfigurationElement<Integer> scoreLimitConfig,
                          final GameModeConfigurationElement<Integer> timeLimitInMinutesConfig,
                          final GameModeConfigurationElement<Boolean> foodEnabledConfig) {
        this(numberOfTeamsConfig,
            randomizedTeamsConfig,
            scoreLimitConfig,
            timeLimitInMinutesConfig,
            foodEnabledConfig,
            List.of()
        );
    }

    GameModeConfiguration(final GameModeConfigurationElement<Integer> numberOfTeamsConfig,
                          final GameModeConfigurationElement<Boolean> randomizedTeamsConfig,
                          final GameModeConfigurationElement<Integer> scoreLimitConfig,
                          final GameModeConfigurationElement<Integer> timeLimitInMinutesConfig,
                          final GameModeConfigurationElement<Boolean> foodEnabledConfig,
                          final List<GameModeConfigurationElement<Integer>> customConfigs) {
        this.numberOfTeamsConfig = numberOfTeamsConfig;
        this.randomizedTeamsConfig = randomizedTeamsConfig;
        this.scoreLimitConfig = scoreLimitConfig;
        this.timeLimitInMinutesConfig = timeLimitInMinutesConfig;
        this.foodEnabledConfig = foodEnabledConfig;
        teams = new ArrayList<>();
        registerNewTeams(numberOfTeamsConfig.getDefaultValue());

        mandatoryConfigMap = new LinkedHashMap<>();
        mandatoryConfigMap.put(numberOfTeamsConfig.getName(), numberOfTeamsConfig);
        mandatoryConfigMap.put(randomizedTeamsConfig.getName(), randomizedTeamsConfig);
        mandatoryConfigMap.put(scoreLimitConfig.getName(), scoreLimitConfig);
        mandatoryConfigMap.put(timeLimitInMinutesConfig.getName(), timeLimitInMinutesConfig);
        mandatoryConfigMap.put(foodEnabledConfig.getName(), foodEnabledConfig);

        customConfigMap = customConfigs
            .stream()
            .collect(Collectors.toMap(GameModeConfigurationElement::getName, config -> config));
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

    // TODO: Should probably snapshot (OPPA) the online player list when initiating select game mode, that way there's
    //  something to reset to if players all agree to reset the team selection
    public void resetTeams() {
        teams.forEach(GameModeTeam::reset);
    }

    public void reset() {
        mandatoryConfigMap.values().forEach(GameModeConfigurationElement::resetToDefault);
        customConfigMap.values().forEach(GameModeConfigurationElement::resetToDefault);
        registerNewTeams(numberOfTeamsConfig.getDefaultValue());
    }
    
}
