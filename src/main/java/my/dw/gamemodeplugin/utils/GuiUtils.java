package my.dw.gamemodeplugin.utils;

import my.dw.gamemodeplugin.ui.BaseInventoryGui;
import my.dw.gamemodeplugin.ui.ChildInventoryGui;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.HotbarGui;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.selectgamemode.SelectGameModeGui;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GuiUtils {

    public static final Map<String, InventoryGui> NAME_TO_UNIQUE_GUI;
    public static final Map<Inventory, InventoryGui> INVENTORY_TO_GUI;
    public static final Map<ItemStack, HotbarGui> ITEM_TRIGGER_TO_GUI;

    static {
        NAME_TO_UNIQUE_GUI = new HashMap<>();
        INVENTORY_TO_GUI = new HashMap<>();
        ITEM_TRIGGER_TO_GUI = new HashMap<>();
        registerBaseInventoryGuis(new SelectGameModeGui());
    }

    private static void registerBaseInventoryGuis(final InventoryGui... guis) {
        for (InventoryGui gui: guis) {
            if (!(gui instanceof BaseInventoryGui)) {
                throw new IllegalArgumentException("Only base guis can be registered from the base method");
            }

            INVENTORY_TO_GUI.put(gui.getInventory(), gui);
            NAME_TO_UNIQUE_GUI.put(gui.getName(), gui);
            registerChildInventoryGuis(gui);
        }
    }

    private static void registerChildInventoryGuis(final InventoryGui gui) {
        if (gui.getChildGuis() == null || gui.getChildGuis().isEmpty()) {
            return;
        }

        gui.getChildGuis().stream()
            .filter(childGui -> childGui instanceof HotbarGui)
            .map(childGui -> (HotbarGui) childGui)
            .forEach(childGui -> {
                childGui.getBlockToGuiFunction()
                    .keySet()
                    .forEach(key -> ITEM_TRIGGER_TO_GUI.put(key, childGui));
                childGui.getItemToGuiFunction()
                    .keySet()
                    .forEach(key -> ITEM_TRIGGER_TO_GUI.put(key, childGui));
            });

        gui.getChildGuis().stream()
            .filter(childGui -> childGui instanceof ChildInventoryGui)
            .map(childGui -> (ChildInventoryGui) childGui)
            .forEach(childGui -> {
                if (INVENTORY_TO_GUI.containsKey(childGui.getInventory())) {
                    return;
                }
                INVENTORY_TO_GUI.put(childGui.getInventory(), childGui);
                if (childGui.getType() == GuiType.UNIQUE) {
                    NAME_TO_UNIQUE_GUI.put(childGui.getName(), childGui);
                }
                registerChildInventoryGuis(childGui);
            });
    }

}
