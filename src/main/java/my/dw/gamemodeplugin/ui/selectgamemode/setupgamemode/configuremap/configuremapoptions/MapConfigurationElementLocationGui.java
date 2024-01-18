package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuremap.configuremapoptions;

import my.dw.gamemodeplugin.model.MapConfigurationElement;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.HotbarGui;
import my.dw.gamemodeplugin.ui.InventoryGui;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class MapConfigurationElementLocationGui extends HotbarGui {

    private final int locationIndex;

    public MapConfigurationElementLocationGui(final String name,
                                              final InventoryGui parentGui,
                                              final MapConfigurationElement mapConfigurationElement,
                                              final int locationIndex) {
        super(name, GuiType.COMMON, 9, parentGui);
        this.locationIndex = locationIndex;

        final ItemStack setLocationItem = createDisplayItem(Material.WHITE_BANNER, name);
        final Consumer<BlockPlaceEvent> setLocationFunction = event -> {
            final Location blockLocation = event.getBlock().getLocation();
            mapConfigurationElement.getLocations().set(locationIndex, blockLocation);
            event.getPlayer().sendMessage(mapConfigurationElement.getItemName() + " " + locationIndex
                + " has been set to " + mapConfigurationElement.getLocationAsString(locationIndex));
        };
        addGuiBlock(setLocationItem, setLocationFunction);
    }

    public int getLocationIndex() {
        return locationIndex;
    }

}
