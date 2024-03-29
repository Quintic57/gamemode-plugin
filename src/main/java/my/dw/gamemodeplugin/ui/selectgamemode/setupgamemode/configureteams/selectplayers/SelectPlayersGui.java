package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.selectplayers;

import static my.dw.gamemodeplugin.utils.GameModeUtils.MAX_NUMBER_OF_TEAMS;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.ChildInventoryGui;
import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.InventoryGuiFunction;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.TeamSelectionBaseGui;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.IntStream;

//TODO: Create a round robin team picker. Alternate opening the inventory between the team captains until all players
// are chosen. Send message to each captain to indicate which captain is currently choosing and the results of the choice.
// Have cancel button on UI that resets back to choosing captains.
public class SelectPlayersGui extends ChildInventoryGui implements DynamicInventory {
    
    private final GameMode gameMode;

    public SelectPlayersGui(final InventoryGui parentGui, final GameMode gameMode) {
        super("Select Players", GuiType.COMMON, (int) Math.ceil((MAX_NUMBER_OF_TEAMS + 1) / 9.0) * 9, parentGui);

        this.gameMode = gameMode;
        IntStream.range(0, MAX_NUMBER_OF_TEAMS).forEach(i -> {
            final ChildInventoryGui teamGui = new TeamSelectionBaseGui(
                "Select Team " + (i + 1) + " Players",
                36,
                this,
                () -> gameMode.getConfiguration().getTeams().get(i).getPlayerList(),
                player -> gameMode.getConfiguration().getTeams().get(i).getPlayerList().add(player),
                gameMode
            );
            addChildGui(teamGui);
        });
    }

    @Override
    public void refreshInventory() {
        clearInventory();

        getChildGuis()
            .stream()
            .filter(gui -> gui instanceof TeamSelectionBaseGui)
            .map(gui -> (TeamSelectionBaseGui) gui)
            .limit(gameMode.getConfiguration().getNumberOfTeamsConfig().getCurrentValue())
            .forEach(gui -> {
                final ItemStack guiItem = createDisplayItem(
                    Material.PAPER,
                    gui.getName(),
                    List.of(gui.getConfigValue())
                );
                final InventoryGuiFunction guiFunction = event -> gui.openInventory(event.getWhoClicked());
                addGuiItem(guiItem, guiFunction);
            });
    }

}
