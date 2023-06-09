package my.dw.gamemodeplugin.ui.gamemodesetup;

import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

//TODO: Implement
public class ConfigureGameMode extends InventoryGui {

    public ConfigureGameMode(final String parentGuiName) {
        super("Configure Game Mode", parentGuiName, 9);
        for (InventoryGui childGui: getNameToChildGuis().values()) {
            final ItemStack guiKey = createDisplayItem(Material.PAPER, childGui.getGuiName(), List.of());
            final GuiFunction guiFunction = event -> childGui.openInventory(event.getWhoClicked());
            displayItemMap.put(guiKey, guiFunction);
            inventory.addItem(guiKey);
        }
    }

}
