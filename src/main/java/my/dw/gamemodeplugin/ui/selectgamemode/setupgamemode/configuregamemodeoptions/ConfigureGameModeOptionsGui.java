package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuregamemodeoptions;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.ChildInventoryGui;
import my.dw.gamemodeplugin.ui.ConfiguredInventory;
import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.InventoryGuiFunction;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Stream;

public class ConfigureGameModeOptionsGui extends ChildInventoryGui implements ConfiguredInventory, DynamicInventory {

    private final GameMode gameMode;

    public ConfigureGameModeOptionsGui(final InventoryGui parentGui, final GameMode gameMode) {
        super("Game Mode Options", GuiType.COMMON, 9, parentGui);

        this.gameMode = gameMode;

        // Configurations with additional functions
        final ChildInventoryGui numberOfTeamsConfigGui = new GameModeConfigurationElementGui<>(
            "Number of Teams",
            9,
            this,
            gameMode.getConfiguration().getNumberOfTeamsConfig(),
            () -> gameMode.getConfiguration().registerNewTeams(
                gameMode.getConfiguration().getNumberOfTeamsConfig().getCurrentValue())
        );
        addChildGui(numberOfTeamsConfigGui);

        // All other configurations
        Stream.of(gameMode.getConfiguration().getMandatoryConfigMap(), gameMode.getConfiguration().getCustomConfigMap())
            .flatMap(map -> map.entrySet().stream())
            .filter(entry -> !List.of("Number of Teams").contains(entry.getKey()))
            .forEach(entry -> {
                final ChildInventoryGui configGui = new GameModeConfigurationElementGui<>(
                    entry.getKey(),
                    9,
                    this,
                    entry.getValue()
                );
                addChildGui(configGui);
            });
//        gameMode.getConfiguration().getMandatoryConfigMap().forEach((name, config) -> {
//            final ChildInventoryGui configGui = new GameModeConfigurationBaseGui<>(
//                name,
//                9,
//                this,
//                config
//            );
//            addChildGui(configGui);
//        });
//        gameMode.getConfiguration().getCustomConfigMap().forEach((name, config) -> {
//            final ChildInventoryGui customConfigGui = new GameModeConfigurationBaseGui<>(
//                name,
//                9,
//                this,
//                config
//            );
//            addChildGui(customConfigGui);
//        });
    }

    @Override
    public void refreshInventory() {
        clearInventory();

        getChildGuis()
            .stream()
            .filter(gui -> gui instanceof GameModeConfigurationElementGui)
            .map(gui -> (GameModeConfigurationElementGui<?>) gui)
            .forEach(gui -> {
                final ItemStack guiItem = createDisplayItem(
                    Material.PAPER,
                    gui.getName(),
                    List.of(gui.getCurrentConfigValueAsString())
                );
                final InventoryGuiFunction guiFunction = event -> gui.openInventory(event.getWhoClicked());
                addGuiItem(guiItem, guiFunction);
            });

        final ItemStack resetConfigurationItem = createDisplayItem(Material.PAPER, "Reset Configuration");
        final InventoryGuiFunction resetConfigurationFunction = event -> {
            gameMode.getConfiguration().reset();
            refreshInventory();
        };
        setGuiFunction(ItemKey.generate(resetConfigurationItem), resetConfigurationFunction);
        getInventory().setItem(7, resetConfigurationItem);
    }

    @Override
    public boolean isConfigured() {
        return true;
    }

}
