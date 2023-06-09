package my.dw.gamemodeplugin.utils;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.SelectGameModeGui;

import java.util.HashMap;
import java.util.Map;

public class GuiUtils {

    public static final Map<String, InventoryGui> NAME_TO_BASE_GUIS;

    public static GameMode currentGameMode;

    static {
        NAME_TO_BASE_GUIS = new HashMap<>();
        registerGuis(new SelectGameModeGui());
    }

    private static void registerGuis(final InventoryGui... guis) {
        for (InventoryGui gui: guis) {
            NAME_TO_BASE_GUIS.put(gui.getGuiName(), gui);
        }
    }

}
