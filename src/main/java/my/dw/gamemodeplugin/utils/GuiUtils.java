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

    public static GameMode currentGameMode;

    static {
        NAME_TO_BASE_GUI = new HashMap<>();
        INVENTORY_TO_GUI = new HashMap<>();
        registerGuis(new SelectGameModeGui());
    }

    private static void registerGuis(final InventoryGui... guis) {
        for (InventoryGui gui: guis) {
            NAME_TO_BASE_GUI.put(gui.getGuiName(), gui);
            INVENTORY_TO_GUI.put(gui.getInventory(), gui);
        }
    }

}
