package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuregamemodeoptions;

import my.dw.gamemodeplugin.model.GameModeConfigurationElement;
import my.dw.gamemodeplugin.ui.ChildInventoryGui;
import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.InventoryGuiFunction;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.InventoryGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameModeConfigurationElementGui<T> extends ChildInventoryGui implements DynamicInventory {

    private final GameModeConfigurationElement<T> configurationValue;
    private final Runnable additionalFunction;

    public GameModeConfigurationElementGui(final String guiName,
                                           final int inventorySize,
                                           final InventoryGui parentGui,
                                           final GameModeConfigurationElement<T> configurationValue) {
        this(guiName, inventorySize, parentGui, configurationValue, null);
    }

    public GameModeConfigurationElementGui(final String guiName,
                                           final int inventorySize,
                                           final InventoryGui parentGui,
                                           final GameModeConfigurationElement<T> configurationValue,
                                           final Runnable additionalFunction) {
        super(guiName, GuiType.COMMON, inventorySize, parentGui);
        this.configurationValue = configurationValue;
        this.additionalFunction = additionalFunction;
    }

    @Override
    public void refreshInventory() {
        clearInventory();

        configurationValue.getValueRange().forEach(value -> {
            final ItemStack guiItem = createDisplayItem(Material.PAPER, value.toString());
            final InventoryGuiFunction guiFunction = event -> {
                configurationValue.setValue(value);
                if (additionalFunction != null) {
                    additionalFunction.run();
                }
                ((DynamicInventory) getParentGui()).refreshInventory();
                final Player player = (Player) event.getWhoClicked();
                getParentGui().openInventory(player);
            };
            addGuiItem(guiItem, guiFunction);
        });
    }

    public String getCurrentConfigValueAsString() {
        return "Current Value: " + configurationValue.getCurrentValue();
    }
}
