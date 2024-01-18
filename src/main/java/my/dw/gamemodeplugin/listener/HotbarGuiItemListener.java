package my.dw.gamemodeplugin.listener;

import static my.dw.gamemodeplugin.utils.GuiUtils.ITEM_TRIGGER_TO_GUI;

import my.dw.gamemodeplugin.ui.HotbarGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class HotbarGuiItemListener implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(final PlayerInteractEvent event) {
        if (!isHotBarGuiItemEvent(event)) {
            return;
        }

        final HotbarGui gui = ITEM_TRIGGER_TO_GUI.get(event.getItem());
        if (gui.handleOnHotbarGuiItemEvent(event)) {
            event.setCancelled(true);
        }
    }

    private boolean isHotBarGuiItemEvent(final PlayerInteractEvent event) {
        return (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            && event.getItem() != null
            && ITEM_TRIGGER_TO_GUI.containsKey(event.getItem());
    }

}
