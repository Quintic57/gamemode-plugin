package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.selectcaptains;

import static my.dw.gamemodeplugin.utils.GameModeUtils.MAX_NUMBER_OF_TEAMS;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.ChildGui;
import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.TeamSelectionBaseGui;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class SelectCaptainsGui extends ChildGui implements DynamicInventory {
    
    private final GameMode gameMode;

    public SelectCaptainsGui(final InventoryGui parentGui, final GameMode gameMode) {
        super("Select Captains", GuiType.COMMON, (int) Math.ceil((MAX_NUMBER_OF_TEAMS + 1) / 9.0) * 9, parentGui);

        this.gameMode = gameMode;
        IntStream.range(0, MAX_NUMBER_OF_TEAMS).forEach(i -> {
            final ChildGui teamGui = new TeamSelectionBaseGui(
                "Select Team " + (i + 1) + " Captain",
                36,
                this,
                () -> gameMode.getCurrentConfiguration().getTeams().get(i).getCaptain() != null
                    ? Set.of(gameMode.getCurrentConfiguration().getTeams().get(i).getCaptain())
                    : Set.of(),
                captain -> gameMode.getCurrentConfiguration().getTeams().get(i).setCaptain(captain),
                gameMode
            );
            childGuis.add(teamGui);
        });
    }

    @Override
    public void refreshInventory() {
        clearInventory();

        childGuis
            .stream()
            .filter(gui -> gui instanceof TeamSelectionBaseGui)
            .map(gui -> (TeamSelectionBaseGui) gui)
            .limit(gameMode.getCurrentConfiguration().getTeams().size())
            .forEach(gui -> {
                final ItemStack guiItem = createDisplayItem(
                    Material.PAPER,
                    gui.getName(),
                    List.of(gui.getConfigValue())
                );
                final GuiFunction guiFunction = event -> gui.openInventory(event.getWhoClicked());
                itemToGuiFunction.put(ItemKey.generate(guiItem), guiFunction);
                inventory.addItem(guiItem);
            });
    }

}
