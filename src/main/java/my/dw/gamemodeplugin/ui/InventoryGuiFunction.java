package my.dw.gamemodeplugin.ui;

import org.bukkit.event.inventory.InventoryClickEvent;

// TODO: Should be using Consumer<InventoryClickEvent> instead
@FunctionalInterface
public interface InventoryGuiFunction {

    void execute(final InventoryClickEvent event);

}
