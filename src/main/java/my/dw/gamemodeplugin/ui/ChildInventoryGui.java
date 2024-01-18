package my.dw.gamemodeplugin.ui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public abstract class ChildInventoryGui extends InventoryGui {

    private final InventoryGui parentGui;

    public ChildInventoryGui(final String name,
                             final GuiType type,
                             final int inventorySize,
                             final InventoryGui parentGui) {
        super(name, type, inventorySize);
        if (parentGui == null) {
            throw new IllegalArgumentException("Parent GUI can not be null for ChildInventoryGuis");
        }
        this.parentGui = parentGui;

        final ItemStack backButtonItem = createDisplayItem(Material.BARRIER, "Go Back");
        final InventoryGuiFunction backButtonFunction = event -> {
            final Player player = (Player) event.getWhoClicked();
            if (parentGui instanceof DynamicInventory) {
                ((DynamicInventory) parentGui).refreshInventory();
            }
            parentGui.openInventory(player);
        };
        setGuiFunction(ItemKey.generate(backButtonItem), backButtonFunction);
        getInventory().setItem(inventorySize - 1, backButtonItem);
    }

    protected InventoryGui getParentGui() {
        return parentGui;
    }

    @Override
    protected void clearInventory() {
        final ItemStack backButton = getInventory().getItem(getInventory().getSize() - 1);
        if (backButton == null) {
            throw new IllegalStateException("Back button can never be null");
        }
        getInventory().clear();
        getInventory().setItem(getInventory().getSize() - 1, backButton);
        getItemToGuiFunction().keySet().retainAll(Set.of(ItemKey.generate(backButton)));
    }

}
