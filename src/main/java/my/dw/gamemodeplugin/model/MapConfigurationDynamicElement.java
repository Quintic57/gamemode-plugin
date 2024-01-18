package my.dw.gamemodeplugin.model;

import java.util.function.Supplier;

public class MapConfigurationDynamicElement extends MapConfigurationElement {

    private final Supplier<GameModeConfigurationElement<Integer>> numberOfLocationsFunction;

    public MapConfigurationDynamicElement(final String name,
                                          final String itemName,
                                          final int maxNumberOfLocations,
                                          final Supplier<GameModeConfigurationElement<Integer>> numberOfLocationsFunction) {
        super(name, itemName, maxNumberOfLocations);
        this.numberOfLocationsFunction = numberOfLocationsFunction;
    }

    @Override
    public Integer getNumberOfLocations() {
        return numberOfLocationsFunction.get().getCurrentValue();
    }
}
