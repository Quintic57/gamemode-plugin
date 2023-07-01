package my.dw.gamemodeplugin.ui.selectgamemode;

import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

//TODO: Implement
public class ConfigureGameModeGui extends InventoryGui {

    public static final String DEFAULT_NAME = "Configure Game Mode";

    public ConfigureGameModeGui(final InventoryGui parentGui) {
        this(DEFAULT_NAME, parentGui);
    }

    public ConfigureGameModeGui(final String guiName, final InventoryGui parentGui) {
        super(guiName, parentGui, 9);
        for (InventoryGui childGui: getNameToChildGuis().values()) {
            final ItemStack guiKey = createDisplayItem(Material.PAPER, childGui.getGuiName(), List.of());
            final GuiFunction guiFunction = event -> childGui.openInventory(event.getWhoClicked());
            displayItemMap.put(guiKey, guiFunction);
            inventory.addItem(guiKey);
        }
    }

}
