package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode;

import my.dw.gamemodeplugin.ui.ConfiguredInventory;
import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuregamemodeoptions.ConfigureGameModeOptionsGui;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.ConfigureTeamsGui;
import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SetUpGameModeGui extends InventoryGui implements DynamicInventory {

    public static final String DEFAULT_NAME = "Set Up Game Mode";

    private static final String CONFIGURED = "Configured";

    private static final String NOT_CONFIGURED = "Not Configured";

    public SetUpGameModeGui(final InventoryGui parentGui) {
        this(DEFAULT_NAME, parentGui);
    }

    public SetUpGameModeGui(final String guiName, final InventoryGui parentGui) {
        super(guiName, 9, parentGui);

        final InventoryGui configureGameModeGui = new ConfigureGameModeOptionsGui(this);
        childGuis.put(configureGameModeGui.getInventory(), configureGameModeGui);
        final InventoryGui configureTeamsGui = new ConfigureTeamsGui(this);
        childGuis.put(configureTeamsGui.getInventory(), configureTeamsGui);
    }

    @Override
    public void refreshInventory() {
        clearInventory();

        for (InventoryGui childGui: childGuis.values()) {
            if (!(childGui instanceof ConfiguredInventory)) {
                continue;
            }

            final ItemStack guiItem = createDisplayItem(
                Material.PAPER,
                childGui.getGuiName(),
                List.of(((ConfiguredInventory) childGui).isConfigured() ? CONFIGURED : NOT_CONFIGURED)
            );
            final GuiFunction guiFunction = event -> childGui.openInventory(event.getWhoClicked());
            itemToGuiFunction.put(ItemKey.generate(guiItem), guiFunction);
            inventory.addItem(guiItem);
        }
        if (GuiUtils.currentGameMode.getCurrentConfiguration().getNumberOfTeamsConfig().getValue() == 0) {
            itemToGuiFunction.put(new ItemKey(Material.PAPER, ConfigureTeamsGui.DEFAULT_NAME),
                event -> event.getWhoClicked().sendMessage("Button disabled, current Number of Teams is 0"));
        }

        final ItemStack startGameItem = createDisplayItem(Material.SPECTRAL_ARROW, "Start Game");
        final GuiFunction startGameFunction = event -> {
            final Set<ItemStack> unconfiguredOptions = Arrays.stream(inventory.getStorageContents())
                .limit(childGuis.values().size())
                .filter(item -> item.getItemMeta().getLore().contains(NOT_CONFIGURED))
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
