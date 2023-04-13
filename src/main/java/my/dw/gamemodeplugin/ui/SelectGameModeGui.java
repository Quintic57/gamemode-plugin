package my.dw.gamemodeplugin.ui;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.selectgamemode.GameModeSetupGui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;
import java.util.List;

public class SelectGameModeGui extends InventoryGui {

    public static final String DEFAULT_NAME = "Select Game Mode";

    public SelectGameModeGui() {
        super(DEFAULT_NAME, (int) Math.ceil((GameMode.values().length + 1) / 9.0) * 9, null);
        final InventoryGui gameModeSetup = new GameModeSetupGui(this);
        childGuis.put(gameModeSetup.getInventory(), gameModeSetup);

        for (GameMode gm: GameMode.values()) {
            final GuiFunction guiFunction = event -> {
                final Player player = (Player) event.getWhoClicked();
                gm.getHandler().before();
                childGuis.get(gameModeSetup.getInventory()).openInventory(player);
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
