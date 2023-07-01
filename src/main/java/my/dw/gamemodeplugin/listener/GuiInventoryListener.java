package my.dw.gamemodeplugin.listener;

import com.google.common.collect.Streams;
import my.dw.gamemodeplugin.exception.NoPlayerToTargetException;
import my.dw.gamemodeplugin.ui.InventoryGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

import static my.dw.gamemodeplugin.utils.GuiUtils.NAME_TO_BASE_GUI;

public class GuiInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClickEvent(final InventoryClickEvent event) {
        if (isInventoryGuiEvent(event)) {
            final InventoryGui gui = NAME_TO_BASE_GUI.get(event.getView().getTitle());
            final Player player = (Player) event.getWhoClicked();

            try {
                event.setCancelled(true);
                gui.handleOnInventoryClickEvent(event);
            } catch (NoPlayerToTargetException e) {
                player.sendMessage(e.getMessage());
            }
        }
    }

    private boolean isInventoryGuiEvent(final InventoryClickEvent event) {
        return event.getWhoClicked() instanceof Player
            && !event.getView().getTitle().isEmpty()
            && Streams.concat(
                NAME_TO_BASE_GUI.keySet().stream(),
                NAME_TO_BASE_GUI.values().stream().flatMap(gui -> gui.getNameToChildGuis().keySet().stream())
            ).anyMatch(gui -> gui.equals(event.getView().getTitle()))
            && Objects.nonNull(event.getCurrentItem());
    }

}
