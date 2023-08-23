package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuregamemodeoptions;

import my.dw.gamemodeplugin.ui.ConfiguredInventory;
import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ConfigureGameModeOptionsGui extends InventoryGui implements ConfiguredInventory, DynamicInventory {

    public static final String DEFAULT_NAME = "Game Mode Options";

    public ConfigureGameModeOptionsGui(final InventoryGui parentGui) {
        this(DEFAULT_NAME, parentGui);
    }

    public ConfigureGameModeOptionsGui(final String guiName, final InventoryGui parentGui) {
        super(guiName, 9, parentGui);

        final InventoryGui numberOfTeamsGui = new OptionConfigurationBaseGui<>(
            "Number of Teams",
            9,
            this,
            () -> GuiUtils.currentGameMode.getCurrentConfiguration().getNumberOfTeamsConfig(),
            value -> {
                GuiUtils.currentGameMode.getCurrentConfiguration().setNumberOfTeamsValue(value);
                GuiUtils.currentGameMode.getCurrentConfiguration().registerNewTeams(value);
            });
        childGuis.put(numberOfTeamsGui.getInventory(), numberOfTeamsGui);
        final InventoryGui randomizedTeamsGui = new OptionConfigurationBaseGui<>(
            "Randomized Teams",
            9,
            this,
            () -> GuiUtils.currentGameMode.getCurrentConfiguration().getRandomizedTeamsConfig(),
            value -> GuiUtils.currentGameMode.getCurrentConfiguration().setRandomizedTeamsValue(value));
        childGuis.put(randomizedTeamsGui.getInventory(), randomizedTeamsGui);
        final InventoryGui scoreLimitGui = new OptionConfigurationBaseGui<>(
            "Score Limit",
            9,
            this,
            () -> GuiUtils.currentGameMode.getCurrentConfiguration().getScoreLimitConfig(),
            value -> GuiUtils.currentGameMode.getCurrentConfiguration().setScoreLimitValue(value));
        childGuis.put(scoreLimitGui.getInventory(), scoreLimitGui);
        final InventoryGui timeLimitGui = new OptionConfigurationBaseGui<>(
            "Time Limit (Minutes)",
            9,
            this,
            () -> GuiUtils.currentGameMode.getCurrentConfiguration().getTimeLimitInMinutesConfig(),
            value -> GuiUtils.currentGameMode.getCurrentConfiguration().setTimeLimitInMinutesValue(value));
        childGuis.put(timeLimitGui.getInventory(), timeLimitGui);
        final InventoryGui foodEnabledGui = new OptionConfigurationBaseGui<>(
            "Food Enabled",
            9,
            this,
            () -> GuiUtils.currentGameMode.getCurrentConfiguration().getFoodEnabledConfig(),
            value -> GuiUtils.currentGameMode.getCurrentConfiguration().setFoodEnabledValue(value));
        childGuis.put(foodEnabledGui.getInventory(), foodEnabledGui);
    }

    @Override
    public void refreshInventory() {
        clearInventory();

        childGuis.entrySet()
            .stream()
            .filter(guiEntry -> guiEntry.getValue() instanceof OptionConfigurationBaseGui)
            .map(guiEntry -> (OptionConfigurationBaseGui<?>) guiEntry.getValue())
            .forEach(gui -> {
                final ItemStack guiItem = createDisplayItem(
                    Material.PAPER,
                    gui.getGuiName(),
                    List.of(gui.getConfigValue())
                );
                final GuiFunction guiFunction = event -> gui.openInventory(event.getWhoClicked());
                itemToGuiFunction.put(ItemKey.generate(guiItem), guiFunction);
                inventory.addItem(guiItem);
            });

        final ItemStack resetConfigurationItem = createDisplayItem(Material.PAPER, "Reset Configuration");
        final GuiFunction resetConfigurationFunction = event -> {
            GuiUtils.currentGameMode.getCurrentConfiguration().reset();
            refreshInventory();
        };
        this.itemToGuiFunction.put(ItemKey.generate(resetConfigurationItem), resetConfigurationFunction);
        this.inventory.setItem(7, resetConfigurationItem);
    }

    @Override
    public boolean isConfigured() {
        return true;
    }

}
