package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuremap;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.ChildInventoryGui;
import my.dw.gamemodeplugin.ui.Gui;
import my.dw.gamemodeplugin.ui.HotbarGui;
import my.dw.gamemodeplugin.ui.InventoryGuiFunction;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuremap.configuremapoptions.ConfigureMapOptionsGui;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuremap.setboundaries.SetBoundariesGui;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ConfigureMapGui extends ChildInventoryGui {

    private final GameMode gameMode;

    public ConfigureMapGui(final InventoryGui parentGui, final GameMode gameMode) {
        super("Configure Map", GuiType.COMMON, 9, parentGui);
        this.gameMode = gameMode;

        final HotbarGui setBoundariesGui = new SetBoundariesGui(this, gameMode);
        addChildGui(setBoundariesGui);
        final ChildInventoryGui configureMapOptionsGui = new ConfigureMapOptionsGui(this, gameMode);
        addChildGui(configureMapOptionsGui);

        for (final Gui gui: getChildGuis()) {
            final ItemStack guiItem = createDisplayItem(Material.PAPER, gui.getName());
            final InventoryGuiFunction guiFunction = event -> gui.openInventory(event.getWhoClicked());
            addGuiItem(guiItem, guiFunction);
        }
    }

}
