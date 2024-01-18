package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.ChildInventoryGui;
import my.dw.gamemodeplugin.ui.ConfiguredInventory;
import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.Gui;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.InventoryGuiFunction;
import my.dw.gamemodeplugin.ui.ItemKey;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.selectcaptains.SelectCaptainsGui;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.selectplayers.SelectPlayersGui;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ConfigureTeamsGui extends ChildInventoryGui implements ConfiguredInventory, DynamicInventory {
    
    private final GameMode gameMode;

    public ConfigureTeamsGui(final InventoryGui parentGui, final GameMode gameMode) {
        super("Configure Teams", GuiType.COMMON, 9, parentGui);

        this.gameMode = gameMode;
        final ChildInventoryGui selectCaptainsGui = new SelectCaptainsGui(this, gameMode);
        addChildGui(selectCaptainsGui);
        final ChildInventoryGui selectTeamsGui = new SelectPlayersGui(this, gameMode);
        addChildGui(selectTeamsGui);
    }

    @Override
    public void refreshInventory() {
        clearInventory();

        for (final Gui childGui: getChildGuis()) {
            final ItemStack guiItem = createDisplayItem(Material.PAPER, childGui.getName());
            final InventoryGuiFunction guiFunction = gameMode.getConfiguration().getRandomizedTeamsConfig().getCurrentValue()
                ? event -> event.getWhoClicked().sendMessage("Button disabled, Randomized Teams configuration is set to true")
                : event -> childGui.openInventory(event.getWhoClicked());
            addGuiItem(guiItem, guiFunction);
        }
        final ItemStack resetConfigurationItem = createDisplayItem(Material.PAPER, "Reset Configuration");
        final InventoryGuiFunction resetConfigurationFunction = event
            -> gameMode.getConfiguration().resetTeams();
        setGuiFunction(ItemKey.generate(resetConfigurationItem), resetConfigurationFunction);
        getInventory().setItem(7, resetConfigurationItem);
    }

    @Override
    public boolean isConfigured() {
        if (gameMode.getConfiguration().getNumberOfTeamsConfig().getCurrentValue() == 0) {
            return true;
        }

        // TODO: Can this be cleaner?
//        final Set<UUID> currentCaptains = gameMode.getCurrentConfiguration().getTeams()
//            .stream()
//            .map(GameModeTeam::getCaptain)
//            .collect(Collectors.toSet());
//        final Set<UUID> currentPlayers = gameMode.getCurrentConfiguration().getTeams()
//            .stream()
//            .flatMap(team -> team.getPlayerList().stream())
//            .collect(Collectors.toSet());
//        final Set<UUID> allPlayers = Sets.union(currentCaptains, currentPlayers);

        return
            gameMode.getConfiguration().getTeams()
            .stream()
            .noneMatch(team -> team.getCaptain() == null);
    }

}
