package my.dw.gamemodeplugin.ui.selectgamemode;

import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.selectgamemode.gamemodesetup.ConfigureGameModeGui;
import my.dw.gamemodeplugin.ui.selectgamemode.gamemodesetup.SelectTeamGui;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GameModeSetupGui extends InventoryGui {

    public static final String DEFAULT_NAME = "Game Mode Setup";

    //TODO: I think we have to define the gui hierarchy within the classes, else NAME_TO_INVENTORY_GUI.get("{guiName}")
    // always returns null since all the guis are being created at the same time
    public GameModeSetupGui(final InventoryGui parentGui) {
        this(DEFAULT_NAME, parentGui);
    }

    public GameModeSetupGui(final String guiName, final InventoryGui parentGui) {
        super(guiName, 18, parentGui);
        final InventoryGui configureGameModeGui = new ConfigureGameModeGui(this);
        childGuis.put(configureGameModeGui.getInventory(), configureGameModeGui);
        final InventoryGui selectTeamGui = new SelectTeamGui(this);
        childGuis.put(selectTeamGui.getInventory(), selectTeamGui);

        for (InventoryGui childGui: this.childGuis.values()) {
            final ItemStack guiKey = createDisplayItem(Material.PAPER, childGui.getGuiName());
            final GuiFunction guiFunction = event -> childGui.openInventory(event.getWhoClicked());
            displayItemMap.put(guiKey, guiFunction);
            inventory.addItem(guiKey);
        }
        for (int i = 9; i < this.inventory.getSize() && i - 9 < this.displayItemMap.keySet().size(); i++) {
            this.inventory.setItem(i, createDisplayItem(Material.RED_WOOL, "Not Configured"));
        }
    }

}
