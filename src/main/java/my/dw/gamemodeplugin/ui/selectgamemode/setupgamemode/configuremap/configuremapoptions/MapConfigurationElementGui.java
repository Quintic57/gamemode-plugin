package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuremap.configuremapoptions;

import my.dw.gamemodeplugin.model.MapConfigurationElement;
import my.dw.gamemodeplugin.ui.ChildInventoryGui;
import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.HotbarGui;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.InventoryGuiFunction;
import my.dw.gamemodeplugin.ui.ItemKey;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public class MapConfigurationElementGui extends ChildInventoryGui implements DynamicInventory {

    private final MapConfigurationElement element;

    public MapConfigurationElementGui(final String guiName,
                                      final int inventorySize,
                                      final InventoryGui parentGui,
                                      final MapConfigurationElement element) {
        super(guiName, GuiType.COMMON, inventorySize, parentGui);
        this.element = element;

        for (int i = 0; i < element.getMaxNumberOfLocations(); i++) {
            final HotbarGui gui = new MapConfigurationElementLocationGui(
                "Configure" + element.getItemName() + " " + i,
                this,
                element,
                i
            );
            addChildGui(gui);
        }
    }

    @Override
    public void refreshInventory() {
        clearInventory();

        getChildGuis()
            .stream()
            .filter(gui -> gui instanceof MapConfigurationElementLocationGui)
            .map(gui -> (MapConfigurationElementLocationGui) gui)
            .limit(element.getNumberOfLocations())
            .forEach(gui -> {
                final ItemStack guiItem = createDisplayItem(
                    Material.PAPER,
                    gui.getName(),
                    List.of(element.getLocationAsString(gui.getLocationIndex()))
                );
                final InventoryGuiFunction guiFunction = event -> gui.openInventory(event.getWhoClicked());
                addGuiItem(guiItem, guiFunction);
            });

        final ItemStack resetConfigurationItem = createDisplayItem(Material.PAPER, "Reset Configuration");
        final InventoryGuiFunction resetConfigurationFunction = event -> {
            element.getLocations().clear();
            refreshInventory();
        };
        setGuiFunction(ItemKey.generate(resetConfigurationItem), resetConfigurationFunction);
        getInventory().setItem(getInventory().getSize() - 2, resetConfigurationItem);
    }
}
