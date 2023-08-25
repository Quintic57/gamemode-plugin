package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams;

import com.google.common.collect.Sets;
import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.model.GameModeTeam;
import my.dw.gamemodeplugin.ui.ChildGui;
import my.dw.gamemodeplugin.ui.ConfiguredInventory;
import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.selectcaptains.SelectCaptainsGui;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.selectplayers.SelectPlayersGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ConfigureTeamsGui extends ChildGui implements ConfiguredInventory, DynamicInventory {
    
    private final GameMode gameMode;

    public ConfigureTeamsGui(final InventoryGui parentGui, final GameMode gameMode) {
        super("Configure Teams", GuiType.COMMON, 9, parentGui);

        this.gameMode = gameMode;
        final ChildGui selectCaptainsGui = new SelectCaptainsGui(this, gameMode);
        childGuis.add(selectCaptainsGui);
        final ChildGui selectTeamsGui = new SelectPlayersGui(this, gameMode);
        childGuis.add(selectTeamsGui);
    }

    @Override
    public void refreshInventory() {
        clearInventory();

        for (ChildGui childGui: childGuis) {
            final ItemStack guiItem = createDisplayItem(Material.PAPER, childGui.getName());
            final GuiFunction guiFunction = gameMode.getCurrentConfiguration().getRandomizedTeamsConfig().getValue()
                ? event -> event.getWhoClicked().sendMessage("Button disabled, Randomized Teams configuration is set to true")
                : event -> childGui.openInventory(event.getWhoClicked());
            itemToGuiFunction.put(ItemKey.generate(guiItem), guiFunction);
            inventory.addItem(guiItem);
        }
        final ItemStack resetConfigurationItem = createDisplayItem(Material.PAPER, "Reset Configuration");
        final GuiFunction resetConfigurationFunction = event
            -> gameMode.getCurrentConfiguration().resetTeams();
        this.itemToGuiFunction.put(ItemKey.generate(resetConfigurationItem), resetConfigurationFunction);
        this.inventory.setItem(7, resetConfigurationItem);
    }

    @Override
    public boolean isConfigured() {
        if (gameMode.getCurrentConfiguration().getNumberOfTeamsConfig().getValue() == 0) {
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
            gameMode.getCurrentConfiguration().getTeams()
            .stream()
            .noneMatch(team -> team.getCaptain() == null);
    }

}
