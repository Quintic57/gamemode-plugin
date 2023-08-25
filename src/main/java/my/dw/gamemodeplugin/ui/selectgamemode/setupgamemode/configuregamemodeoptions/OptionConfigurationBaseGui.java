package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuregamemodeoptions;

import my.dw.gamemodeplugin.model.ConfigurationValue;
import my.dw.gamemodeplugin.ui.ChildGui;
import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class OptionConfigurationBaseGui<T> extends ChildGui implements DynamicInventory {

    private final Supplier<ConfigurationValue<T>> getterFunction;

    private final Consumer<T> setterFunction;

    public OptionConfigurationBaseGui(final String guiName,
                                      final int inventorySize,
                                      final InventoryGui parentGui,
                                      final Supplier<ConfigurationValue<T>> getterFunction,
                                      final Consumer<T> setterFunction) {
        super(guiName, GuiType.COMMON, inventorySize, parentGui);
        this.getterFunction = getterFunction;
        this.setterFunction = setterFunction;
    }

    @Override
    public void refreshInventory() {
        clearInventory();

        getterFunction.get().getValueRange().forEach(value -> {
            final ItemStack guiItem = createDisplayItem(Material.PAPER, value.toString());
            final GuiFunction guiFunction = event -> {
                setterFunction.accept(value);
                ((DynamicInventory) parentGui).refreshInventory();
                final Player player = (Player) event.getWhoClicked();
                parentGui.openInventory(player);
            };
            itemToGuiFunction.put(ItemKey.generate(guiItem), guiFunction);
            inventory.addItem(guiItem);
        });
    }

    public String getConfigValue() {
        return "Current Value: " + getterFunction.get().getValue();
    }
}
