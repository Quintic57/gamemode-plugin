package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams;

import com.google.common.collect.Sets;
import my.dw.gamemodeplugin.model.GameModeTeam;
import my.dw.gamemodeplugin.ui.ConfiguredInventory;
import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.selectcaptains.SelectCaptainsGui;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams.selectplayers.SelectPlayersGui;
import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ConfigureTeamsGui extends InventoryGui implements ConfiguredInventory, DynamicInventory {

    public static final String DEFAULT_NAME = "Configure Teams";

    public ConfigureTeamsGui(final InventoryGui parentGui) {
        this(DEFAULT_NAME, parentGui);
    }

    public ConfigureTeamsGui(final String guiName, final InventoryGui parentGui) {
        super(guiName, 9, parentGui);

        final InventoryGui selectCaptainsGui = new SelectCaptainsGui(this);
        childGuis.put(selectCaptainsGui.getInventory(), selectCaptainsGui);
        final InventoryGui selectTeamsGui = new SelectPlayersGui(this);
        childGuis.put(selectTeamsGui.getInventory(), selectTeamsGui);
    }

    @Override
    public void refreshInventory() {
        clearInventory();

        for (InventoryGui childGui: childGuis.values()) {
            final ItemStack guiItem = createDisplayItem(Material.PAPER, childGui.getGuiName());
            final GuiFunction guiFunction = randomizedTeamsIsActive()
                ? event -> event.getWhoClicked().sendMessage("Button disabled, Randomized Teams configuration is set to true")
                : event -> childGui.openInventory(event.getWhoClicked());
            itemToGuiFunction.put(ItemKey.generate(guiItem), guiFunction);
            inventory.addItem(guiItem);
        }
        final ItemStack resetConfigurationItem = createDisplayItem(Material.PAPER, "Reset Configuration");
        final GuiFunction resetConfigurationFunction = event
            -> GuiUtils.currentGameMode.getCurrentConfiguration().resetTeams();
        this.itemToGuiFunction.put(ItemKey.generate(resetConfigurationItem), resetConfigurationFunction);
        this.inventory.setItem(7, resetConfigurationItem);
    }

    @Override
    public boolean isConfigured() {
        if (GuiUtils.currentGameMode == null) {
            return false;
        }
        if (GuiUtils.currentGameMode.getCurrentConfiguration().getNumberOfTeamsConfig().getValue() == 0) {
            return true;
        }

        // TODO: Can this be cleaner?
        final Set<UUID> currentCaptains = GuiUtils.currentGameMode.getCurrentConfiguration().getTeams()
            .stream()
            .map(GameModeTeam::getCaptain)
            .collect(Collectors.toSet());
        final Set<UUID> currentPlayers = GuiUtils.currentGameMode.getCurrentConfiguration().getTeams()
            .stream()
            .flatMap(team -> team.getPlayerList().stream())
            .collect(Collectors.toSet());
        final Set<UUID> allPlayers = Sets.union(currentCaptains, currentPlayers);

        return
            GuiUtils.currentGameMode.getCurrentConfiguration().getTeams()
            .stream()
            .noneMatch(team -> team.getCaptain() == null || team.getPlayerList().isEmpty())
                && Bukkit.getOnlinePlayers().stream().allMatch(player -> allPlayers.contains(player.getUniqueId()));
    }

    private boolean randomizedTeamsIsActive() {
        return GuiUtils.currentGameMode != null
            && GuiUtils.currentGameMode.getCurrentConfiguration().getRandomizedTeamsConfig().getValue();
    }

}
