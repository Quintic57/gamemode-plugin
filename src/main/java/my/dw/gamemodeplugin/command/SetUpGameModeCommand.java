package my.dw.gamemodeplugin.command;

import static my.dw.gamemodeplugin.utils.GuiUtils.NAME_TO_BASE_GUI;

import my.dw.gamemodeplugin.ui.InventoryGui;
import my.dw.gamemodeplugin.ui.selectgamemode.SelectGameModeGui;
import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetUpGameModeCommand implements CommandExecutor {

    private static final InventoryGui SET_UP_GAME_MODE;

    // TODO: Change this if you decide to implement the unique/common GUI model
    static {
        SET_UP_GAME_MODE = NAME_TO_BASE_GUI.get(SelectGameModeGui.DEFAULT_NAME).getChildGuis().values()
            .stream()
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            if (GuiUtils.currentGameMode == null) {
                player.sendMessage("There is no game mode currently being configured.");
                return false;
            }

            SET_UP_GAME_MODE.openInventory(player);
            return true;
        }

        return false;
    }

}
