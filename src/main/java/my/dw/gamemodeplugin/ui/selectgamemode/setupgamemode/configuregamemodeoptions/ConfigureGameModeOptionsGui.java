package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuregamemodeoptions;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.ChildGui;
import my.dw.gamemodeplugin.ui.ConfiguredInventory;
import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ConfigureGameModeOptionsGui extends ChildGui implements ConfiguredInventory, DynamicInventory {

    private final GameMode gameMode;

    public ConfigureGameModeOptionsGui(final InventoryGui parentGui, final GameMode gameMode) {
        super("Game Mode Options", GuiType.COMMON, 9, parentGui);

        this.gameMode = gameMode;
        final ChildGui numberOfTeamsGui = new OptionConfigurationBaseGui<>(
            "Number of Teams",
            9,
            this,
            () -> gameMode.getCurrentConfiguration().getNumberOfTeamsConfig(),
            value -> {
                gameMode.getCurrentConfiguration().setNumberOfTeamsValue(value);
                gameMode.getCurrentConfiguration().registerNewTeams(value);
            });
        addChildGui(numberOfTeamsGui);
        final ChildGui randomizedTeamsGui = new OptionConfigurationBaseGui<>(
            "Randomized Teams",
            9,
            this,
            () -> gameMode.getCurrentConfiguration().getRandomizedTeamsConfig(),
            value -> gameMode.getCurrentConfiguration().setRandomizedTeamsValue(value));
        addChildGui(randomizedTeamsGui);
        final ChildGui scoreLimitGui = new OptionConfigurationBaseGui<>(
            "Score Limit",
            9,
            this,
            () -> gameMode.getCurrentConfiguration().getScoreLimitConfig(),
            value -> gameMode.getCurrentConfiguration().setScoreLimitValue(value));
        addChildGui(scoreLimitGui);
        final ChildGui timeLimitGui = new OptionConfigurationBaseGui<>(
            "Time Limit (Minutes)",
            9,
            this,
            () -> gameMode.getCurrentConfiguration().getTimeLimitInMinutesConfig(),
            value -> gameMode.getCurrentConfiguration().setTimeLimitInMinutesValue(value));
        addChildGui(timeLimitGui);
        final ChildGui foodEnabledGui = new OptionConfigurationBaseGui<>(
            "Food Enabled",
            9,
            this,
            () -> gameMode.getCurrentConfiguration().getFoodEnabledConfig(),
            value -> gameMode.getCurrentConfiguration().setFoodEnabledValue(value));
        addChildGui(foodEnabledGui);
    }

    @Override
    public void refreshInventory() {
        clearInventory();

        getChildGuis()
            .stream()
            .filter(gui -> gui instanceof OptionConfigurationBaseGui)
            .map(gui -> (OptionConfigurationBaseGui<?>) gui)
            .forEach(gui -> {
                final ItemStack guiItem = createDisplayItem(
                    Material.PAPER,
                    gui.getName(),
                    List.of(gui.getConfigValue())
                );
                final GuiFunction guiFunction = event -> gui.openInventory(event.getWhoClicked());
                addGuiItem(guiItem, guiFunction);
            });

        final ItemStack resetConfigurationItem = createDisplayItem(Material.PAPER, "Reset Configuration");
        final GuiFunction resetConfigurationFunction = event -> {
            gameMode.getCurrentConfiguration().reset();
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
