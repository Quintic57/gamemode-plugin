package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.selectcaptains;

import static my.dw.gamemodeplugin.utils.GuiUtils.MAX_NUMBER_OF_TEAMS;

import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.TeamConfigurationBaseGui;
import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class SelectCaptainsGui extends InventoryGui implements DynamicInventory {

    public static final String DEFAULT_NAME = "Select Captains";

    public SelectCaptainsGui(final InventoryGui parentGui) {
        this(DEFAULT_NAME, parentGui);
    }

    public SelectCaptainsGui(final String guiName, final InventoryGui parentGui) {
        super(guiName, (int) Math.ceil((MAX_NUMBER_OF_TEAMS + 1) / 9.0) * 9, parentGui);

        IntStream.range(0, MAX_NUMBER_OF_TEAMS).forEach(i -> {
            final InventoryGui teamGui = new TeamConfigurationBaseGui(
                "Select Team " + (i + 1) + " Captain",
                36,
                this,
                () -> GuiUtils.currentGameMode.getCurrentConfiguration().getTeams().get(i).getCaptain() != null
                    ? Set.of(GuiUtils.currentGameMode.getCurrentConfiguration().getTeams().get(i).getCaptain())
                    : Set.of(),
                captain -> GuiUtils.currentGameMode.getCurrentConfiguration().getTeams().get(i).setCaptain(captain)
            );
            childGuis.put(teamGui.getInventory(), teamGui);
        });
    }

    @Override
    public void refreshInventory() {
        clearInventory();

        childGuis.entrySet()
            .stream()
            .filter(guiEntry -> guiEntry.getValue() instanceof TeamConfigurationBaseGui)
            .map(guiEntry -> (TeamConfigurationBaseGui) guiEntry.getValue())
            .limit(GuiUtils.currentGameMode.getCurrentConfiguration().getTeams().size())
            .forEach(gui -> {
                final ItemStack guiItem = createDisplayItem(
                    Material.PAPER,
                    gui.getGuiName(),
                    List.of(gui.getConfigValue())
                );
                final GuiFunction guiFunction = event -> gui.openInventory(event.getWhoClicked());
                itemToGuiFunction.put(ItemKey.generate(guiItem), guiFunction);
                inventory.addItem(guiItem);
            });
    }

}
