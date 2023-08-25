package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configureteams;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.model.GameModeTeam;
import my.dw.gamemodeplugin.ui.ChildGui;
import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.GuiType;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TeamSelectionBaseGui extends ChildGui implements DynamicInventory {

    private final Supplier<Set<UUID>> getterFunction;

    private final Consumer<UUID> setterFunction;
    
    private final GameMode gameMode;

    public TeamSelectionBaseGui(final String guiName,
                                final int inventorySize,
                                final InventoryGui parentGui,
                                final Supplier<Set<UUID>> getterFunction,
                                final Consumer<UUID> setterFunction,
                                final GameMode gameMode) {
        super(guiName, GuiType.COMMON, inventorySize, parentGui);
        this.getterFunction = getterFunction;
        this.setterFunction = setterFunction;
        this.gameMode = gameMode;
    }

    @Override
    public void refreshInventory() {
        clearInventory();

        final Set<UUID> currentCaptains = gameMode.getCurrentConfiguration().getTeams()
            .stream()
            .map(GameModeTeam::getCaptain)
            .collect(Collectors.toSet());
        final Set<UUID> currentPlayers = gameMode.getCurrentConfiguration().getTeams()
            .stream()
            .flatMap(team -> team.getPlayerList().stream())
            .collect(Collectors.toSet());
        Bukkit.getOnlinePlayers()
            .stream()
            .filter(player -> !currentCaptains.contains(player.getUniqueId()))
            .filter(player -> !currentPlayers.contains(player.getUniqueId()))
            .sorted(Comparator.comparing(Player::getDisplayName, String.CASE_INSENSITIVE_ORDER))
            .forEach(player -> {
                //TODO: have this use player's skull instead
                final ItemStack guiItem = createDisplayItem(Material.SKELETON_SKULL, player.getDisplayName());
                final GuiFunction guiFunction = event -> {
                    setterFunction.accept(player.getUniqueId());
                    ((DynamicInventory) parentGui).refreshInventory();
                    final Player teamCaptain = (Player) event.getWhoClicked();
                    parentGui.openInventory(teamCaptain);
                };
                itemToGuiFunction.put(ItemKey.generate(guiItem), guiFunction);
                inventory.addItem(guiItem);
        });
    }

    public String getConfigValue() {
        if (getterFunction.get() == null || getterFunction.get().isEmpty()) {
            return "[Unassigned]";
        }

        return getterFunction.get()
            .stream()
            .filter(playerUUID -> Bukkit.getPlayer(playerUUID) != null)
            .map(playerUUID -> Bukkit.getPlayer(playerUUID).getName())
            .collect(Collectors.joining());
    }
}
