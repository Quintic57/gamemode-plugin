package my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode;

import my.dw.gamemodeplugin.ui.DynamicInventory;
import my.dw.gamemodeplugin.ui.GuiFunction;
import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.ItemKey;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuregamemodeoptions.ConfigureNumberOfTeamsGui;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuregamemodeoptions.ConfigureRandomizedTeamsGui;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuregamemodeoptions.ConfigureScoreLimitGui;
import my.dw.gamemodeplugin.ui.selectgamemode.setupgamemode.configuregamemodeoptions.ConfigureTeamsGui;
import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigureGameModeOptionsGui extends InventoryGui implements DynamicInventory {

    public static final String DEFAULT_NAME = "Game Mode Options";

    public ConfigureGameModeOptionsGui(final InventoryGui parentGui) {
        this(DEFAULT_NAME, parentGui);
    }

    public ConfigureGameModeOptionsGui(final String guiName, final InventoryGui parentGui) {
        super(guiName, 9, parentGui);

        final InventoryGui configureNumberOfTeamsGui = new ConfigureNumberOfTeamsGui(this);
        childGuis.put(configureNumberOfTeamsGui.getInventory(), configureNumberOfTeamsGui);
        final InventoryGui configureRandomizedTeamsGui = new ConfigureRandomizedTeamsGui(this);
        childGuis.put(configureRandomizedTeamsGui.getInventory(), configureRandomizedTeamsGui);
        final InventoryGui configureTeamsGui = new ConfigureTeamsGui(this);
        childGuis.put(configureTeamsGui.getInventory(), configureTeamsGui);
        final InventoryGui configureScoreLimitGui = new ConfigureScoreLimitGui(this);
        childGuis.put(configureScoreLimitGui.getInventory(), configureScoreLimitGui);
        final List<InventoryGui> guis = List.of(
            configureNumberOfTeamsGui,
            configureRandomizedTeamsGui,
            configureTeamsGui,
            configureScoreLimitGui
        );

        for (InventoryGui childGui: guis) {
            final ItemStack guiItem = createDisplayItem(
                Material.PAPER,
                childGui.getGuiName(),
                List.of(getOptionValue(childGui.getGuiName()))
            );
            final GuiFunction guiFunction = event -> childGui.openInventory(event.getWhoClicked());
            itemToGuiFunction.put(ItemKey.generate(guiItem), guiFunction);
            inventory.addItem(guiItem);
        }

        final ItemStack resetConfigurationItem = createDisplayItem(Material.PAPER, "Reset Configuration");
        final GuiFunction resetConfigurationFunction = event -> {
            GuiUtils.currentGameMode.getHandler().getCurrentConfiguration().reset();
            refreshInventory();
        };
        this.itemToGuiFunction.put(ItemKey.generate(resetConfigurationItem), resetConfigurationFunction);
        this.inventory.setItem(7, resetConfigurationItem);
    }

    @Override
    public void refreshInventory() {
        final Set<String> guiNames = childGuis.values()
            .stream()
            .map(InventoryGui::getGuiName)
            .collect(Collectors.toUnmodifiableSet());
        for (final ItemStack guiItem : inventory) {
            if (guiItem == null) {
                continue;
            }
            final ItemMeta itemMeta = guiItem.getItemMeta();
            if (itemMeta != null && guiNames.contains(itemMeta.getDisplayName())) {
                itemMeta.setLore(List.of(getOptionValue(itemMeta.getDisplayName())));
                guiItem.setItemMeta(itemMeta);
            }
            guiItem.setItemMeta(itemMeta);
        }
    }

    private String getOptionValue(final String option) {
        if (GuiUtils.currentGameMode == null) {
            return "Current Value: N/A";
        }

        final StringBuilder output = new StringBuilder("Current Value: ");
        switch(option) {
            case ConfigureNumberOfTeamsGui.DEFAULT_NAME:
                output.append(GuiUtils.currentGameMode.getHandler().getCurrentConfiguration().getNumberOfTeams().getData());
                break;
            case ConfigureRandomizedTeamsGui.DEFAULT_NAME:
                output.append(GuiUtils.currentGameMode.getHandler().getCurrentConfiguration().getRandomizedTeams().getData());
                break;
            case ConfigureScoreLimitGui.DEFAULT_NAME:
                output.append(GuiUtils.currentGameMode.getHandler().getCurrentConfiguration().getScoreLimit().getData());
                break;
            case ConfigureTeamsGui.DEFAULT_NAME:
            default:
                output.append("N/A");
        }
        return output.toString();
    }

}
