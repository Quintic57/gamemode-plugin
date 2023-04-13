package my.dw.gamemodeplugin.ui;

import my.dw.gamemodeplugin.model.GameMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO: May want to consolidate this into a hierarchy... similar to SelectClassGui
public class SelectGameModeGui {

    public static final String GUI_NAME;

    public static final Map<ItemStack, String> DISPLAY_ITEM_TO_GAME_MODE_NAME;

    public static final Inventory INVENTORY;

    static {
        GUI_NAME = "Select GameMode";
        DISPLAY_ITEM_TO_GAME_MODE_NAME = Arrays.stream(GameMode.values())
            .collect(Collectors.toMap(SelectGameModeGui::getDisplayItem, Enum::name));
        INVENTORY = Bukkit.createInventory(null, (int) Math.ceil((GameMode.values().length + 1) / 9.0) * 9, GUI_NAME);
        DISPLAY_ITEM_TO_GAME_MODE_NAME.keySet().stream()
            .sorted((Comparator.comparing(item -> ChatColor.stripColor(item.getItemMeta().getDisplayName()))))
            .forEach(INVENTORY::addItem);
    }

    private static ItemStack getDisplayItem(final GameMode gameMode) {
        switch (gameMode) {
            case DEATHMATCH:
            case DOMINATION:
            default:
                return createDisplayItem(Material.PAPER, gameMode.name(), List.of("No description implemented"));
        }
    }

    private static ItemStack createDisplayItem(final Material itemMaterial,
                                               final String displayName,
                                               final List<String> lore) {
        final ItemStack displayItem = new ItemStack(itemMaterial);
        final ItemMeta meta = displayItem.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        meta.addEnchant(Enchantment.DURABILITY, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        displayItem.setItemMeta(meta);

        return displayItem;
    }

    public static void openInventory(final HumanEntity entity) {
        entity.openInventory(INVENTORY);
    }

}
