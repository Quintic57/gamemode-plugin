package my.dw.gamemodeplugin.utils;

import my.dw.gamemodeplugin.ui.BaseGui;
import my.dw.gamemodeplugin.ui.ChildGui;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.selectgamemode.SelectGameModeGui;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class GuiUtils {

    public static final Map<String, InventoryGui> NAME_TO_UNIQUE_GUI;
    public static final Map<Inventory, InventoryGui> INVENTORY_TO_GUI;

    static {
        NAME_TO_UNIQUE_GUI = new HashMap<>();
        INVENTORY_TO_GUI = new HashMap<>();
        registerBaseGuis(new SelectGameModeGui());
    }

    private static void registerBaseGuis(final InventoryGui... guis) {
        for (InventoryGui gui: guis) {
            if (!(gui instanceof BaseGui)) {
                throw new IllegalArgumentException("Only base guis can be registered from the base method");
            }

            INVENTORY_TO_GUI.put(gui.getInventory(), gui);
            NAME_TO_UNIQUE_GUI.put(gui.getName(), gui);
            registerChildGuis(gui);
        }
    }

    private static void registerChildGuis(final InventoryGui gui) {
        if (gui.getChildGuis() == null || gui.getChildGuis().isEmpty()) {
            return;
        }

        for (final ChildGui childGui: gui.getChildGuis()) {
            if (INVENTORY_TO_GUI.containsKey(childGui.getInventory())) {
                continue;
            }
            INVENTORY_TO_GUI.put(childGui.getInventory(), childGui);
            if (childGui.getType() == GuiType.UNIQUE) {
                NAME_TO_UNIQUE_GUI.put(childGui.getName(), childGui);
            }
            registerChildGuis(childGui);
        }
    }

}
