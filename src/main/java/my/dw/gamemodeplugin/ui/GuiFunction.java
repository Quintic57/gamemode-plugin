package my.dw.gamemodeplugin.ui;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface GuiFunction {

    void execute(final InventoryClickEvent event);

}
