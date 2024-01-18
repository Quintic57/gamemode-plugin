package my.dw.gamemodeplugin.listener;

import static my.dw.gamemodeplugin.utils.GuiUtils.ITEM_TRIGGER_TO_GUI;

import my.dw.gamemodeplugin.ui.HotbarGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class HotbarGuiBlockListener implements Listener {

    @EventHandler
    public void onBlockPlaceEvent(final BlockPlaceEvent event) {
        if (!isHotbarGuiBlockEvent(event)) {
            return;
        }

        final HotbarGui gui = ITEM_TRIGGER_TO_GUI.get(event.getItemInHand());
        if (gui.handleOnHotbarGuiBlockEvent(event)) {
            event.setCancelled(true);
        }
    }

    private boolean isHotbarGuiBlockEvent(final BlockPlaceEvent event) {
        return ITEM_TRIGGER_TO_GUI.containsKey(event.getItemInHand());
    }

}
