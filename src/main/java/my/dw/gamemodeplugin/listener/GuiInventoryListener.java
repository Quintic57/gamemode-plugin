package my.dw.gamemodeplugin.listener;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.SelectGameModeGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

import static my.dw.gamemodeplugin.ui.SelectGameModeGui.DISPLAY_ITEM_TO_GAME_MODE_NAME;

public class GuiInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClickEvent(final InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player) || Objects.isNull(event.getCurrentItem())) {
            return;
        }

        if (event.getView().getTitle().equalsIgnoreCase(SelectGameModeGui.GUI_NAME)) {
            event.setCancelled(true);
            final Player player = (Player) event.getWhoClicked();
            final String gameModeName = DISPLAY_ITEM_TO_GAME_MODE_NAME.get(event.getCurrentItem());

            if (Objects.isNull(gameModeName)) {
                player.sendMessage("This GUI item does not have an associated game mode to start");
                return;
            }

            GameMode.valueOf(gameModeName).before(player);
            player.closeInventory();
        }
    }

}
