package my.dw.gamemodeplugin.ui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class Gui {

    private final String name;
    private final GuiType type;
    private final Inventory inventory;

    public Gui(final String name, final GuiType type, final int inventorySize) {
        this.name = name;
        this.type = type;
        this.inventory = Bukkit.createInventory(null, Math.min(inventorySize, 54), name);
    }

    public String getName() {
        return name;
    }

    public GuiType getType() {
        return type;
    }

    public Inventory getInventory() {
        return inventory;
    }

    protected static ItemStack createDisplayItem(final Material itemMaterial,
                                                 final String displayName) {
        return createDisplayItem(itemMaterial, displayName, List.of());
    }

    protected static ItemStack createDisplayItem(final Material itemMaterial,
                                                 final String displayName,
                                                 final List<String> description) {
        final ItemStack displayItem = new ItemStack(itemMaterial);
        final ItemMeta meta = displayItem.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(description);
        meta.addEnchant(Enchantment.DURABILITY, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        displayItem.setItemMeta(meta);

        return displayItem;
    }

    public abstract void openInventory(final HumanEntity humanEntity);

    protected abstract void clearInventory();

}
