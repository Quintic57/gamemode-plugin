package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.selectplayers;

import static my.dw.gamemodeplugin.utils.GuiUtils.MAX_NUMBER_OF_TEAMS;

import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.TeamConfigurationBaseGui;
import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.IntStream;

//TODO: Create a round robin team picker. Alternate opening the inventory between the team captains until all players
// are chosen. Send message to each captain to indicate which captain is currently choosing and the results of the choice.
// Have cancel button on UI that resets back to choosing captains.
public class SelectPlayersGui extends InventoryGui implements DynamicInventory {

    public static final String DEFAULT_NAME = "Select Players";

    public SelectPlayersGui(final InventoryGui parentGui) {
        this(DEFAULT_NAME, parentGui);
    }

    public SelectPlayersGui(final String guiName, final InventoryGui parentGui) {
        super(guiName, (int) Math.ceil((MAX_NUMBER_OF_TEAMS + 1) / 9.0) * 9, parentGui);

        IntStream.range(0, MAX_NUMBER_OF_TEAMS).forEach(i -> {
            final InventoryGui teamGui = new TeamConfigurationBaseGui(
                "Select Team " + (i + 1) + " Players",
                36,
                this,
                () -> GuiUtils.currentGameMode.getCurrentConfiguration().getTeams().get(i).getPlayerList(),
                player -> GuiUtils.currentGameMode.getCurrentConfiguration().getTeams().get(i).getPlayerList().add(player)
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
            .limit(GuiUtils.currentGameMode.getCurrentConfiguration().getNumberOfTeamsConfig().getValue())
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
