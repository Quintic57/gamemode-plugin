package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuregamemodeoptions;

import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

//TODO: Alternate opening the inventory between the team captains until all players are chosen. Send message to each
// captain to indicate which captain is currently choosing and the results of the choice. Have cancel button on UI that
// resets back to choosing captains.
public class ConfigureTeamsGui extends InventoryGui implements DynamicInventory {

    public static final String DEFAULT_NAME = "Configure Teams";

    // TODO: This should probably be stored in the configuration object
    private final Map<UUID, Set<UUID>> teamCaptainToTeamList;

    public ConfigureTeamsGui(final InventoryGui parentGui) {
        this(DEFAULT_NAME, parentGui);
    }

    public ConfigureTeamsGui(final String guiName, final InventoryGui parentGui) {
        super(guiName, (int) Math.ceil((Bukkit.getOnlinePlayers().size() + 1) / 9.0) * 9, parentGui);

        teamCaptainToTeamList = new LinkedHashMap<>();
        for (Player p: Bukkit.getOnlinePlayers()) {
            //TODO: have this use player's skull instead
            final ItemStack guiItem = createDisplayItem(Material.SKELETON_SKULL, p.getDisplayName());
            final GuiFunction guiFunction = event -> {
                final UUID teamCaptain = event.getWhoClicked().getUniqueId();
                if (teamCaptainToTeamList.containsKey(event.getWhoClicked().getUniqueId())) {
                    teamCaptainToTeamList.get(teamCaptain).add(p.getUniqueId());
                } else {
                    teamCaptainToTeamList.put(teamCaptain, new HashSet<>(Set.of(p.getUniqueId())));
                }
                inventory.removeItem(guiItem);
            };
            itemToGuiFunction.put(ItemKey.generate(guiItem), guiFunction);
            inventory.addItem(guiItem);
        }
    }

    @Override
    public void refreshInventory() {

    }

}
