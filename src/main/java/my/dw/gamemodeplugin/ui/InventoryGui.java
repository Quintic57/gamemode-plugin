package my.dw.gamemodeplugin.ui;

import my.dw.gamemodeplugin.exception.NoPlayerToTargetException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: Might be better to define the gui hierarchy from within the classes, have instance vars called parentGui and childGuis
public abstract class InventoryGui {

    // TODO: If this is going to be a unique identifier, remove this attribute.
    protected final String guiName;

    //TODO: Is it possible to use this unique object as an identifier for inventories, and index it with event.getClickedInventory()?
    protected final Inventory inventory;

    protected final Map<ItemStack, GuiFunction> displayItemMap;

    protected final InventoryGui parentGui;

    protected final Map<Inventory, InventoryGui> nameToChildGuis;

    public InventoryGui(final String guiName, final InventoryGui parentGui, final int inventorySize) {
        this(guiName, parentGui, inventorySize, new HashMap<>());
    }

    public InventoryGui(final String guiName,
                        final InventoryGui parentGui,
                        final int inventorySize,
                        final Map<Inventory, InventoryGui> nameToChildGuis) {
        this.guiName = guiName;
        this.inventory = Bukkit.createInventory(null, inventorySize, this.guiName);
        this.displayItemMap = new HashMap<>();
        this.parentGui = parentGui;
        this.nameToChildGuis = nameToChildGuis;
    }

    protected static ItemStack createDisplayItem(final Material itemMaterial,
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

    public void openInventory(final HumanEntity entity) {
        entity.openInventory(inventory);
    }

    public String getGuiName() {
        return guiName;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Map<Inventory, InventoryGui> getNameToChildGuis() {
        return nameToChildGuis;
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
