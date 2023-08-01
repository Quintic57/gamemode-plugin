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
import java.util.Set;

// TODO: Might have to split this into base/unique/common InventoryGuis, where base/unique guis have unique names and thus can be
//  accessed from anywhere in the code, whereas common guis can have different names and have to be indexed by inventory object
public abstract class InventoryGui {

    protected final String guiName;

    protected final Inventory inventory;

    protected final Map<ItemKey, GuiFunction> itemToGuiFunction;

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
        this.itemToGuiFunction = new HashMap<>();
        this.parentGui = parentGui;
        this.childGuis = childGuis;

        final ItemStack backButtonItem = createDisplayItem(Material.BARRIER, "Go Back");
        final GuiFunction backButtonFunction = event -> {
            final Player player = (Player) event.getWhoClicked();
            if (parentGui == null) {
                player.sendMessage("There is no page to go back to");
                return;
            }
            if (parentGui instanceof DynamicInventory) {
                ((DynamicInventory) parentGui).refreshInventory();
            }
            parentGui.openInventory(player);
        };
        this.itemToGuiFunction.put(ItemKey.generate(backButtonItem), backButtonFunction);
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

    protected void clearInventory() {
        final ItemStack backButton = inventory.getItem(inventory.getSize() - 1);
        if (backButton == null) {
            throw new IllegalStateException("Back button can never be null");
        }

        inventory.clear();
        inventory.setItem(inventory.getSize() - 1, backButton);
        itemToGuiFunction.keySet().retainAll(Set.of(ItemKey.generate(backButton)));
    }

    public void openInventory(final HumanEntity entity) {
        if (this instanceof DynamicInventory) {
            ((DynamicInventory) this).refreshInventory();
        }
        entity.openInventory(inventory);
    }

    public void handleOnInventoryClickEvent(final InventoryClickEvent event) throws NoPlayerToTargetException {
        final GuiFunction guiFunction = itemToGuiFunction.get(ItemKey.generate(event.getCurrentItem()));
        if (guiFunction == null) {
            event.getWhoClicked().sendMessage("There is no functionality implemented for this button");
            return;
        }
        guiFunction.execute(event);
    }

}
