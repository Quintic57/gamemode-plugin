package my.dw.gamemodeplugin.ui.gamemodesetup;

import my.dw.gamemodeplugin.exception.NoPlayerToTargetException;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameModeSetupGui extends InventoryGui {

    public static final String NAME = "Game Mode Setup";

    private static final String SELECT_TEAM_GUI_NAME = "Select Team";

    private static final String GAME_MODE_SETTINGS_GUI_NAME = "Game Mode Settings";

    //TODO: I think we have to define the gui hierarchy within the classes, else NAME_TO_INVENTORY_GUI.get("{guiName}")
    // always returns null since all the guis are being created at the same time
    public GameModeSetupGui(final String parentGuiName) {
        this(NAME, parentGuiName);
    }

    public GameModeSetupGui(final String guiName, final String parentGuiName) {
        super(
            guiName,
            parentGuiName,
            18,
            Map.of(
                GAME_MODE_SETTINGS_GUI_NAME, new ConfigureGameMode(guiName),
                SELECT_TEAM_GUI_NAME, new SelectTeamGui(guiName)
            )
        );

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
