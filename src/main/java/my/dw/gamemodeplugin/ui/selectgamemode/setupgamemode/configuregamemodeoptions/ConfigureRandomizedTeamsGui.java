package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuregamemodeoptions;

import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ConfigureRandomizedTeamsGui extends InventoryGui {

    public static final String DEFAULT_NAME = "Randomized Teams";

    public ConfigureRandomizedTeamsGui(final InventoryGui parentGui) {
        this(DEFAULT_NAME, parentGui);
    }

    public ConfigureRandomizedTeamsGui(final String guiName, final InventoryGui parentGui) {
        super(guiName, 9, parentGui);

        final ItemStack guiItemTrue = createDisplayItem(Material.GREEN_WOOL, "true");
        final GuiFunction guiFunctionTrue = event -> {
            GuiUtils.currentGameMode.getHandler().getCurrentConfiguration().setRandomizedTeams(true);
            ((DynamicInventory) parentGui).refreshInventory();
            final Player player = (Player) event.getWhoClicked();
            parentGui.openInventory(player);
        };
        itemToGuiFunction.put(ItemKey.generate(guiItemTrue), guiFunctionTrue);
        inventory.addItem(guiItemTrue);

        final ItemStack guiItemFalse = createDisplayItem(Material.RED_WOOL, "false");
        final GuiFunction guiFunctionFalse = event -> {
            GuiUtils.currentGameMode.getHandler().getCurrentConfiguration().setRandomizedTeams(false);
            ((DynamicInventory) parentGui).refreshInventory();
            final Player player = (Player) event.getWhoClicked();
            parentGui.openInventory(player);
        };
        itemToGuiFunction.put(ItemKey.generate(guiItemFalse), guiFunctionFalse);
        inventory.addItem(guiItemFalse);
    }

}
