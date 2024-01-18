package my.dw.gamemodeplugin.ui;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

// TODO: Should prob move this to a common library since it's used by both plugins
public abstract class InventoryGui extends Gui {

    private final Map<ItemKey, InventoryGuiFunction> itemToGuiFunction;
    private final Set<Gui> childGuis;

    public InventoryGui(final String name,
                        final GuiType type,
                        final int inventorySize) {
        super(name, type, inventorySize);
        this.itemToGuiFunction = new HashMap<>();
        this.childGuis = new LinkedHashSet<>();
    }

    protected Map<ItemKey, InventoryGuiFunction> getItemToGuiFunction() {
        return itemToGuiFunction;
    }

    protected void setGuiFunction(final ItemKey itemKey, final InventoryGuiFunction guiFunction) {
        itemToGuiFunction.put(itemKey, guiFunction);
    }

    protected void addGuiItem(final ItemStack guiItem, final InventoryGuiFunction guiFunction) {
        itemToGuiFunction.put(ItemKey.generate(guiItem), guiFunction);
        getInventory().addItem(guiItem);
    }

    public Set<Gui> getChildGuis() {
        return childGuis;
    }

    public void addChildGui(final Gui childGui) {
        if (childGui instanceof BaseInventoryGui) {
            throw new IllegalArgumentException("Child gui can not be a base GUI");
        }
        childGuis.add(childGui);
    }

    @Override
    public void openInventory(final HumanEntity entity) {
        if (this instanceof DynamicInventory) {
            ((DynamicInventory) this).refreshInventory();
        }
        entity.openInventory(getInventory());
    }

    public boolean handleOnInventoryClickEvent(final InventoryClickEvent event) {
        final InventoryGuiFunction guiFunction = itemToGuiFunction.get(ItemKey.generate(event.getCurrentItem()));
        if (guiFunction == null) {
            event.getWhoClicked().sendMessage("There is no functionality implemented for this button");
            return false;
        }

        guiFunction.execute(event);
        return true;
    }

}
