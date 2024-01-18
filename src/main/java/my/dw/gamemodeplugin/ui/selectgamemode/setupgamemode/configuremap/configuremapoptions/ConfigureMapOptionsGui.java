package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuremap.configuremapoptions;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.model.MapConfigurationElement;
import my.dw.gamemodeplugin.ui.ChildInventoryGui;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.InventoryGui;

public class ConfigureMapOptionsGui extends ChildInventoryGui {

    public ConfigureMapOptionsGui(final InventoryGui parentGui, final GameMode gameMode) {
        super(
            "Configure Map Options",
            GuiType.COMMON,
            (int) Math.ceil((gameMode.getMapConfiguration().getCustomConfigurations().size() + 1) / 9.0) * 9,
            parentGui
        );

        for (final MapConfigurationElement element: gameMode.getMapConfiguration().getCustomConfigurations()) {
            final ChildInventoryGui elementGui = new MapConfigurationElementGui(
                "Configure " + element.getName(),
                (int) Math.ceil((element.getMaxNumberOfLocations() + 1) / 9.0) * 9,
                this,
                element
            );
            addChildGui(elementGui);
        }

        /*
        Stream.of(gameMode.getConfiguration().getMandatoryConfigMap(), gameMode.getConfiguration().getCustomConfigMap())
            .flatMap(map -> map.entrySet().stream())
            .filter(entry -> !List.of("Number of Teams").contains(entry.getKey()))
            .forEach(entry -> {
                final ChildInventoryGui configGui = new GameModeConfigurationBaseGui<>(
                    entry.getKey(),
                    9,
                    this,
                    entry.getValue()
                );
                addChildGui(configGui);
            });
         */
    }

}
