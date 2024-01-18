package my.dw.gamemodeplugin.model;

import lombok.Getter;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.List;

@Getter
public class MapConfigurationElement {

    private final String name;
    private final String itemName;
    private final int maxNumberOfLocations;
    private final List<Location> locations;

    public MapConfigurationElement(final String name, final String itemName, final int maxNumberOfLocations) {
        this.name = name;
        this.itemName = itemName;
        this.maxNumberOfLocations = maxNumberOfLocations;
        this.locations = Arrays.asList(new Location[maxNumberOfLocations]);
    }

    public Integer getNumberOfLocations() {
        return maxNumberOfLocations;
    }

    public String getLocationAsString(final int locationIndex) {
        if (locations.size() <= locationIndex || locations.get(locationIndex) == null) {
            return "Current Value: N/A";
        }

        return "Current Value: [" + locations.get(locationIndex).getBlockX() + ", "
            + locations.get(locationIndex).getBlockY()+ ", "
            + locations.get(locationIndex).getBlockZ() + "]";
    }

}
