package my.dw.gamemodeplugin.listener;

import static my.dw.gamemodeplugin.utils.GuiUtils.INVENTORY_TO_GUI;

import my.dw.gamemodeplugin.ui.InventoryGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryGuiListener implements Listener {

    @EventHandler
    public void onInventoryClickEvent(final InventoryClickEvent event) {
        if (!isInventoryGuiEvent(event)) {
            return;
        }

        final InventoryGui gui = INVENTORY_TO_GUI.get(event.getClickedInventory());
        if (gui.handleOnInventoryClickEvent(event)) {
            event.setCancelled(true);
        }
    }

    private boolean isInventoryGuiEvent(final InventoryClickEvent event) {
        return event.getWhoClicked() instanceof Player
            && event.getClickedInventory() != null
            && INVENTORY_TO_GUI.containsKey(event.getClickedInventory())
            && event.getCurrentItem() != null;
    }

}
