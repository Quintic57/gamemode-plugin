package my.dw.gamemodeplugin.ui;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.gamemodesetup.GameModeSetupGui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SelectGameModeGui extends InventoryGui {

    public static final String NAME;

    static {
        NAME = "Select Game Mode";
    }

    public SelectGameModeGui() {
        super(
            NAME,
            "",
            (int) Math.ceil((GameMode.values().length + 1) / 9.0) * 9
        );
        final InventoryGui gameModeSetup = new GameModeSetupGui(NAME);
        nameToChildGuis.put(gameModeSetup.guiName, gameModeSetup);

        for (GameMode gm: GameMode.values()) {
            final GuiFunction guiFunction = event -> {
                final Player player = (Player) event.getWhoClicked();
                gm.getHandler().before();
                this.nameToChildGuis.get(GameModeSetupGui.NAME).openInventory(player);
            };
            displayItemMap.put(getDisplayItem(gm), guiFunction);
        }
        // game modes displayed in alphabetical order
        displayItemMap.keySet().stream()
            .sorted((Comparator.comparing(item -> ChatColor.stripColor(item.getItemMeta().getDisplayName()))))
            .forEach(this.inventory::addItem);
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
