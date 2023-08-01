package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuregamemodeoptions;

import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ConfigureNumberOfTeamsGui extends InventoryGui implements DynamicInventory {

    public static final String DEFAULT_NAME = "Number of Teams";

    public ConfigureNumberOfTeamsGui(final InventoryGui parentGui) {
        this(DEFAULT_NAME, parentGui);
    }

    public ConfigureNumberOfTeamsGui(final String guiName, final InventoryGui parentGui) {
        super(guiName, 9, parentGui);
    }

    @Override
    public void refreshInventory() {
        if (GuiUtils.currentGameMode == null) {
            throw new IllegalStateException("Current game mode can not be null when configuring options");
        }

        itemToGuiFunction.clear();
        inventory.remove(Material.PAPER);
        GuiUtils.currentGameMode.getHandler().getCurrentConfiguration().getNumberOfTeams().getValueRange().forEach(num -> {
            final ItemStack guiItem = createDisplayItem(Material.PAPER, Integer.toString(num));
            final GuiFunction guiFunctionTrue = event -> {
                GuiUtils.currentGameMode.getHandler().getCurrentConfiguration().setNumberOfTeams(num);
                ((DynamicInventory) parentGui).refreshInventory();
                final Player player = (Player) event.getWhoClicked();
                parentGui.openInventory(player);
            };
            itemToGuiFunction.put(ItemKey.generate(guiItem), guiFunctionTrue);
            inventory.addItem(guiItem);
        });
    }

}
