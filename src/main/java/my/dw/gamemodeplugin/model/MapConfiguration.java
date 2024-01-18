package my.dw.gamemodeplugin.model;

import static my.dw.gamemodeplugin.utils.GameModeUtils.MAX_NUMBER_OF_TEAMS;

import org.bukkit.Location;

import java.util.Arrays;
import java.util.List;

// TODO: HERE
public enum MapConfiguration {

    CAPTURE_THE_FLAG(
        List.of(
            new MapConfigurationDynamicElement(
                "Flag Coordinates",
                "Flag",
                MAX_NUMBER_OF_TEAMS,
                GameModeConfiguration.CAPTURE_THE_FLAG::getNumberOfTeamsConfig
            )
        )
    ),
    DEATHMATCH(),
    DOMINATION(
        List.of(
            new MapConfigurationDynamicElement(
                "Landmark Coordinates",
                "Landmark",
                10,
                () -> GameModeConfiguration.DOMINATION.getCustomConfigMap().get("Number of Landmarks")
            ),
            new MapConfigurationDynamicElement(
                "Team Base Coordinates",
                "Team Base",
                MAX_NUMBER_OF_TEAMS,
                GameModeConfiguration.DOMINATION::getNumberOfTeamsConfig
            )
        )
    );

    private final List<Location> boundaries;
    private int ceiling;
    private int floor;
    private final List<MapConfigurationElement> customConfigurations;

    MapConfiguration() {
        this(List.of());
    }

    MapConfiguration(final List<MapConfigurationElement> customConfigurations) {
        boundaries = Arrays.asList(new Location[4]);
        this.ceiling = 100;
        this.floor = 0;
        this.customConfigurations = customConfigurations;
    }

    public Location getBoundary(final int index) {
        if (index < 0 || index >= 4) {
            throw new IllegalArgumentException("Map boundary index must be between 0-3");
        }
        return boundaries.get(index);
    }

    public void setBoundary(final int index, final Location location) {
        if (index < 0 || index >= 4) {
            throw new IllegalArgumentException("Map boundary index must be between 0-3");
        }
        boundaries.set(index, location);
    }

    public void setCeiling(int ceiling) {
        this.ceiling = ceiling;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public List<MapConfigurationElement> getCustomConfigurations() {
        return customConfigurations;
    }

    public String getBoundaryAsString(final int index) {
        if (index < 0 || index >= 4) {
            throw new IllegalArgumentException("Map boundary index must be between 0-3");
        }
        if (boundaries.get(index) == null) {
            return "N/A";
        }
        return "(" + boundaries.get(index).getBlockX() + "," + boundaries.get(index).getBlockZ() + ")";
    }

}
