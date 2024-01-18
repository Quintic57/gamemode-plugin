package my.dw.gamemodeplugin.ui;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public abstract class HotbarGui extends Gui {

    private final InventoryGui parentGui;
    private final Map<ItemStack, Consumer<PlayerInteractEvent>> itemToGuiFunction;
    private final Map<ItemStack, Consumer<BlockPlaceEvent>> blockToGuiFunction;

    public HotbarGui(final String name, final GuiType type, final int inventorySize, final InventoryGui parentGui) {
        super(name, type, inventorySize);
        if (this instanceof DynamicInventory) {
            throw new IllegalStateException("HotbarGuis can not be dynamic");
        }
        if (parentGui == null) {
            throw new IllegalArgumentException("Parent GUI can not be null for HotbarGuis");
        }
        this.parentGui = parentGui;
        this.itemToGuiFunction = new HashMap<>();
        this.blockToGuiFunction = new HashMap<>();

        final ItemStack backButtonItem = createDisplayItem(Material.PAPER, "Back to Menu");
        final Consumer<PlayerInteractEvent> backButtonFunction = event -> {
            final Player player = event.getPlayer();
            if (parentGui instanceof DynamicInventory) {
                ((DynamicInventory) parentGui).refreshInventory();
            }
            parentGui.openInventory(player);
        };
        itemToGuiFunction.put(backButtonItem, backButtonFunction);
        getInventory().setItem(inventorySize - 1, backButtonItem);
    }

    public InventoryGui getParentGui() {
        return parentGui;
    }

    public Map<ItemStack, Consumer<PlayerInteractEvent>> getItemToGuiFunction() {
        return itemToGuiFunction;
    }

    public Map<ItemStack, Consumer<BlockPlaceEvent>> getBlockToGuiFunction() {
        return blockToGuiFunction;
    }

    public boolean handleOnHotbarGuiItemEvent(final PlayerInteractEvent event) {
        final Consumer<PlayerInteractEvent> function = itemToGuiFunction.get(event.getItem());
        if (function == null) {
            event.getPlayer().sendMessage("There is no functionality implemented for this item");
            return false;
        }

        function.accept(event);
        return true;
    }

    public boolean handleOnHotbarGuiBlockEvent(final BlockPlaceEvent event) {
        final Consumer<BlockPlaceEvent> function = blockToGuiFunction.get(event.getItemInHand());
        if (function == null) {
            event.getPlayer().sendMessage("There is no functionality implemented for this block");
            return false;
        }

        function.accept(event);
        return true;
    }

    protected void addGuiItem(final ItemStack guiItem, final Consumer<PlayerInteractEvent> guiFunction) {
        itemToGuiFunction.put(guiItem, guiFunction);
        getInventory().addItem(guiItem);
    }

    protected void addGuiBlock(final ItemStack guiItem, final Consumer<BlockPlaceEvent> guiFunction) {
        blockToGuiFunction.put(guiItem, guiFunction);
        getInventory().addItem(guiItem);
    }

    @Override
    public void openInventory(final HumanEntity humanEntity) {
        humanEntity.getInventory().clear();
        humanEntity.getInventory().addItem(getInventory().getContents());
    }

    @Override
    protected void clearInventory() {
        final ItemStack backButton = getInventory().getItem(getInventory().getSize() - 1);
        if (backButton == null) {
            throw new IllegalStateException("Back button can never be null");
        }
        getInventory().clear();
        getInventory().setItem(getInventory().getSize() - 1, backButton);
        itemToGuiFunction.keySet().retainAll(Set.of(backButton));
    }

}
