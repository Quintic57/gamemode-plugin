package my.dw.gamemodeplugin.ui;

import my.dw.gamemodeplugin.exception.NoPlayerToTargetException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InventoryGui {

    protected final String guiName;

    //TODO: Is it possible to use this unique object as an identifier for inventories, and index it with event.getClickedInventory()?
    protected final Inventory inventory;

    protected final Map<ItemStack, GuiFunction> displayItemMap;

    protected final InventoryGui parentGui;

    protected final Map<Inventory, InventoryGui> childGuis;

    public InventoryGui(final String guiName, final int inventorySize, final InventoryGui parentGui) {
        this(guiName, inventorySize, parentGui, new HashMap<>());
    }

    public InventoryGui(final String guiName,
                        final int inventorySize,
                        final InventoryGui parentGui,
                        final Map<Inventory, InventoryGui> childGuis) {
        this.guiName = guiName;
        this.inventory = Bukkit.createInventory(null, inventorySize, guiName);
        this.displayItemMap = new HashMap<>();
        this.parentGui = parentGui;
        this.childGuis = childGuis;

        final ItemStack backButtonItem = createDisplayItem(Material.BARRIER, "Go Back");
        final GuiFunction backButtonFunction = event -> {
            final Player player = (Player) event.getWhoClicked();
            if (this.parentGui == null) {
                player.sendMessage("There is no page to go back to");
                return;
            }
            this.parentGui.openInventory(player);
        };
        this.displayItemMap.put(backButtonItem, backButtonFunction);
        this.inventory.setItem(inventorySize - 1, backButtonItem);
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

    public String getGuiName() {
        return guiName;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Map<Inventory, InventoryGui> getChildGuis() {
        return childGuis;
    }

    public void openInventory(final HumanEntity entity) {
        entity.openInventory(inventory);
    }

    public void handleOnInventoryClickEvent(final InventoryClickEvent event) throws NoPlayerToTargetException {
        final GuiFunction guiFunction = displayItemMap.get(event.getCurrentItem());
        if (guiFunction == null) {
            //TODO: should we send a message to player to notify that the button has no functionality?
//            event.getWhoClicked().sendMessage("There is no functionality implemented for this button");
            return;
        }
        guiFunction.execute(event);
    }

}
