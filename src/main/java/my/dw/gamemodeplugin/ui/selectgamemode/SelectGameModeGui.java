package my.dw.gamemodeplugin.ui.selectgamemode;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.BaseInventoryGui;
import my.dw.gamemodeplugin.ui.ChildInventoryGui;
import my.dw.gamemodeplugin.ui.InventoryGuiFunction;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.SetUpGameModeGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SelectGameModeGui extends BaseInventoryGui {

    public static final String NAME = "Select Game Mode";

    public SelectGameModeGui() {
        super(NAME, (int) Math.ceil((GameMode.values().length + 1) / 9.0) * 9);

        // game modes displayed in alphabetical order
        Arrays.stream(GameMode.values()).sorted((Comparator.comparing(Enum::name))).forEach(gm -> {
            final ChildInventoryGui setUpGameMode = new SetUpGameModeGui(this, gm);
            addChildGui(setUpGameMode);

            final ItemStack guiItem = getDisplayItem(gm);
            final InventoryGuiFunction guiFunction = event -> {
                final Player player = (Player) event.getWhoClicked();
                gm.getHandler().before(player);
                setUpGameMode.openInventory(player);
            };
            addGuiItem(guiItem, guiFunction);
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
