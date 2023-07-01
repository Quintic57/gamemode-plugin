package my.dw.gamemodeplugin.ui.selectgamemode;

import my.dw.gamemodeplugin.exception.NoPlayerToTargetException;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class GameModeSetupGui extends InventoryGui {

    public static final String DEFAULT_NAME = "Game Mode Setup";

    //TODO: I think we have to define the gui hierarchy within the classes, else NAME_TO_INVENTORY_GUI.get("{guiName}")
    // always returns null since all the guis are being created at the same time
    public GameModeSetupGui(final InventoryGui parentGui) {
        this(DEFAULT_NAME, parentGui);
    }

    public GameModeSetupGui(final String guiName, final InventoryGui parentGui) {
        super(
            guiName,
            parentGui,
            18
        );
        final InventoryGui configureGameModeGui = new ConfigureGameModeGui(this);
        nameToChildGuis.put(configureGameModeGui.getInventory(), configureGameModeGui);
        final InventoryGui selectTeamGui = new SelectTeamGui(this);
        nameToChildGuis.put(selectTeamGui.getInventory(), selectTeamGui);

        for (InventoryGui childGui: this.nameToChildGuis.values()) {
            final ItemStack guiKey = createDisplayItem(Material.PAPER, childGui.getGuiName(), List.of());
            final GuiFunction guiFunction = event -> childGui.openInventory(event.getWhoClicked());
            displayItemMap.put(guiKey, guiFunction);
            inventory.addItem(guiKey);
        }
        for (int i = 9; i < this.inventory.getSize() && i - 9 < this.displayItemMap.keySet().size(); i++) {
            this.inventory.setItem(i, createDisplayItem(Material.RED_WOOL, "Not Configured", List.of()));
        }
    }

    @Override
    public void handleOnInventoryClickEvent(final InventoryClickEvent event) throws NoPlayerToTargetException {
        final InventoryGui gui = (InventoryGui) this.displayItemMap.get(event.getCurrentItem());
//        System.out.println(NAME_TO_BASE_GUI);

        if (Objects.isNull(gui)) {
            return;
        }

        gui.openInventory(event.getWhoClicked());
    }

}
