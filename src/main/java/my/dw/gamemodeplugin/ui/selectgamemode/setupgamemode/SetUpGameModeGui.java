package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.ChildGui;
import my.dw.gamemodeplugin.ui.ConfiguredInventory;
import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuregamemodeoptions.ConfigureGameModeOptionsGui;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.ConfigureTeamsGui;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SetUpGameModeGui extends ChildGui implements DynamicInventory {

    private static final String CONFIGURED = "Configured";

    private static final String NOT_CONFIGURED = "Not Configured";

    private final GameMode gameMode;

    private final ChildGui configureTeamsGui;

    public SetUpGameModeGui(final InventoryGui parentGui, final GameMode gameMode) {
        super("Set Up Game Mode", GuiType.COMMON, 9, parentGui);

        this.gameMode = gameMode;
        final ChildGui configureGameModeGui = new ConfigureGameModeOptionsGui(this, gameMode);
        childGuis.add(configureGameModeGui);
        this.configureTeamsGui = new ConfigureTeamsGui(this, gameMode);
        childGuis.add(configureTeamsGui);
    }

    @Override
    public void refreshInventory() {
        clearInventory();

        for (final ChildGui childGui: childGuis) {
            if (!(childGui instanceof ConfiguredInventory)) {
                continue;
            }

            final ItemStack guiItem = createDisplayItem(
                Material.PAPER,
                childGui.getName(),
                List.of(((ConfiguredInventory) childGui).isConfigured() ? CONFIGURED : NOT_CONFIGURED)
            );
            final GuiFunction guiFunction = event -> childGui.openInventory(event.getWhoClicked());
            itemToGuiFunction.put(ItemKey.generate(guiItem), guiFunction);
            inventory.addItem(guiItem);
        }
        if (gameMode.getCurrentConfiguration().getNumberOfTeamsConfig().getValue() == 0) {
            itemToGuiFunction.put(new ItemKey(Material.PAPER, configureTeamsGui.getName()),
                event -> event.getWhoClicked().sendMessage("Button disabled, current Number of Teams is 0"));
        }

        final ItemStack startGameItem = createDisplayItem(Material.SPECTRAL_ARROW, "Start Game");
        final GuiFunction startGameFunction = event -> {
            final Set<ItemStack> unconfiguredOptions = Arrays.stream(inventory.getStorageContents())
                .limit(childGuis.size())
                .filter(item -> item.getItemMeta().getLore().contains(NOT_CONFIGURED))
                .collect(Collectors.toSet());
            if (!unconfiguredOptions.isEmpty()) {
                final String output = unconfiguredOptions.stream()
                    .map(item -> item.getItemMeta().getDisplayName())
                    .collect(Collectors.joining(", "));
                event.getWhoClicked().sendMessage("The following options have not been configured: " + output);
                return;
            }
            gameMode.getHandler().startGame();
            event.getWhoClicked().closeInventory();
        };
        itemToGuiFunction.put(ItemKey.generate(startGameItem), startGameFunction);
        inventory.setItem(7, startGameItem);
    }

}
