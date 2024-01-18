package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuremap.setboundaries;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.HotbarGui;
import my.dw.gamemodeplugin.ui.InventoryGui;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class SetBoundariesGui extends HotbarGui {

    private final GameMode gameMode;

    public SetBoundariesGui(final InventoryGui parentGui, final GameMode gameMode) {
        super("Set Boundaries", GuiType.COMMON, 9, parentGui);
        this.gameMode = gameMode;

        final List<Material> boundaryColors = List.of(
            Material.RED_WOOL,
            Material.GREEN_WOOL,
            Material.BLUE_WOOL,
            Material.GRAY_WOOL
        );
        IntStream.range(0, 4).forEach(i -> {
            final ItemStack boundaryItem = createDisplayItem(
                boundaryColors.get(i),
                "Set Boundary " + (i + 1)
            );
            final Consumer<BlockPlaceEvent> boundaryFunction = event -> {
                final Location blockLocation = event.getBlock().getLocation();
                gameMode.getMapConfiguration().setBoundary(i, blockLocation);
                event.getPlayer().sendMessage("Boundary " + (i + 1) + "has been set to "
                    + gameMode.getMapConfiguration().getBoundaryAsString(i));
            };
            addGuiBlock(boundaryItem, boundaryFunction);
        });

        final ItemStack setCeilingItem = createDisplayItem(Material.BLACK_CONCRETE_POWDER, "Set Ceiling");
        final Consumer<PlayerInteractEvent> setCeilingFunction = event -> {
            final Location playerLocation = event.getPlayer().getLocation();
            gameMode.getMapConfiguration().setCeiling(playerLocation.getBlockY());
            event.getPlayer().sendMessage("Ceiling has been set to " + playerLocation.getBlockY());
        };
        addGuiItem(setCeilingItem, setCeilingFunction);
        final ItemStack setFloorItem = createDisplayItem(Material.WHITE_CONCRETE_POWDER, "Set Floor");
        final Consumer<PlayerInteractEvent> setFloorFunction = event -> {
            final Location playerLocation = event.getPlayer().getLocation();
            gameMode.getMapConfiguration().setFloor(playerLocation.getBlockY());
            event.getPlayer().sendMessage("Floor has been set to " + playerLocation.getBlockY());
        };
        addGuiItem(setFloorItem, setFloorFunction);
    }

    // TODO: Might be able to treat each individual boundary as a MapConfigurationElementItem, which means the sendMessage() isn't necessary
    @Override
    public void openInventory(final HumanEntity humanEntity) {
        humanEntity.sendMessage("Current Configuration:\n"
            + "Boundary 1: " + gameMode.getMapConfiguration().getBoundaryAsString(0) + "\n"
            + "Boundary 2: " + gameMode.getMapConfiguration().getBoundaryAsString(1) + "\n"
            + "Boundary 3: " + gameMode.getMapConfiguration().getBoundaryAsString(2) + "\n"
            + "Boundary 4: " + gameMode.getMapConfiguration().getBoundaryAsString(3)
        );
        super.openInventory(humanEntity);
    }
}
