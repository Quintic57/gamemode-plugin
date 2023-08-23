package my.dw.gamemodeplugin.ui.selectgamemode;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.SetUpGameModeGui;
import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SelectGameModeGui extends InventoryGui {

    public static final String DEFAULT_NAME = "Select Game Mode";

    public SelectGameModeGui() {
        super(DEFAULT_NAME, (int) Math.ceil((GameMode.values().length + 1) / 9.0) * 9, null);

        final InventoryGui setUpGameMode = new SetUpGameModeGui(this);
        childGuis.put(setUpGameMode.getInventory(), setUpGameMode);

        // game modes displayed in alphabetical order
        Arrays.stream(GameMode.values()).sorted((Comparator.comparing(Enum::name))).forEach(gm -> {
            final ItemStack guiItem = getDisplayItem(gm);
            final GuiFunction guiFunction = event -> {
                final Player player = (Player) event.getWhoClicked();
                if (GuiUtils.currentGameMode != null && GuiUtils.currentGameMode != gm) {
                    player.sendMessage("Gamemode " + GuiUtils.currentGameMode.name() + " is currently being configured." +
                        "To continue that setup, type /setupgamemode. To end that setup, type /endgamemode.");
                    return;
                }
                gm.getHandler().before(player);
                childGuis.get(setUpGameMode.getInventory()).openInventory(player);
            };
            itemToGuiFunction.put(ItemKey.generate(guiItem), guiFunction);
            inventory.addItem(guiItem);
        });
    }

    private ItemStack getDisplayItem(final GameMode gameMode) {
        switch (gameMode) {
            case CAPTURE_THE_FLAG:
            case DEATHMATCH:
            case DOMINATION:
            default:
                return createDisplayItem(Material.PAPER, gameMode.name(), List.of("No description implemented"));
        }
    }
}
