package my.dw.gamemodeplugin.ui.selectgamemode;

import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.ConfigureGameModeOptionsGui;
import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SetUpGameModeGui extends InventoryGui {

    public static final String DEFAULT_NAME = "Set Up Game Mode";

    public SetUpGameModeGui(final InventoryGui parentGui) {
        this(DEFAULT_NAME, parentGui);
    }

    public SetUpGameModeGui(final String guiName, final InventoryGui parentGui) {
        super(guiName, 18, parentGui);

        final InventoryGui configureGameModeGui = new ConfigureGameModeOptionsGui(this);
        childGuis.put(configureGameModeGui.getInventory(), configureGameModeGui);

        for (InventoryGui childGui: childGuis.values()) {
            final ItemStack guiItem = createDisplayItem(Material.PAPER, childGui.getGuiName());
            final GuiFunction guiFunction = event -> childGui.openInventory(event.getWhoClicked());
            itemToGuiFunction.put(ItemKey.generate(guiItem), guiFunction);
            inventory.addItem(guiItem);
        }
        for (int i = 9; i < inventory.getSize() && i - 9 < childGuis.values().size(); i++) {
            inventory.setItem(i, createDisplayItem(Material.RED_WOOL, "Not Configured"));
        }

        final ItemStack startGameItem = createDisplayItem(Material.SPECTRAL_ARROW, "Start Game");
        final GuiFunction startGameFunction = event -> {
            final Set<ItemStack> unconfiguredOptions = Arrays.stream(inventory.getStorageContents())
                .skip(9)
                .limit(childGuis.values().size())
                .filter(item -> item.getItemMeta().getDisplayName().equals("Not Configured"))
                .collect(Collectors.toSet());
            if (!unconfiguredOptions.isEmpty()) {
                final String output = unconfiguredOptions.stream()
                    .map(item -> item.getItemMeta().getDisplayName())
                    .collect(Collectors.joining(", "));
                event.getWhoClicked().sendMessage("The following options have not been configured: " + output);
                return;
            }
            GuiUtils.currentGameMode.getHandler().startGame();
        };
        itemToGuiFunction.put(ItemKey.generate(startGameItem), startGameFunction);
        inventory.setItem(8, startGameItem);
    }

}
