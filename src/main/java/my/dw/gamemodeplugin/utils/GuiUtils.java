package my.dw.gamemodeplugin.utils;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.SelectGameModeGui;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class GuiUtils {

    public static final Map<String, InventoryGui> NAME_TO_BASE_GUI;
    public static final Map<Inventory, InventoryGui> INVENTORY_TO_GUI;

    // TODO: This is crusty, should probably be stored as metadata
    public static GameMode currentGameMode;

    static {
        NAME_TO_BASE_GUI = new HashMap<>();
        INVENTORY_TO_GUI = new HashMap<>();
        registerBaseGuis(new SelectGameModeGui());
    }

    private static void registerBaseGuis(final InventoryGui... guis) {
        for (InventoryGui gui: guis) {
            NAME_TO_BASE_GUI.put(gui.getGuiName(), gui);
            INVENTORY_TO_GUI.put(gui.getInventory(), gui);
            registerChildGuis(gui);
        }
    }

    private static void registerChildGuis(final InventoryGui gui) {
        if (gui.getChildGuis() == null || gui.getChildGuis().isEmpty()) {
            return;
        }

        for (final Map.Entry<Inventory, InventoryGui> childGuiEntry: gui.getChildGuis().entrySet()) {
            INVENTORY_TO_GUI.put(childGuiEntry.getKey(), childGuiEntry.getValue());
            registerChildGuis(childGuiEntry.getValue());
        }
    }

}
