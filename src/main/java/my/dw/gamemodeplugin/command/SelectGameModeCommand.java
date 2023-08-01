package my.dw.gamemodeplugin.command;

import my.dw.gamemodeplugin.ui.SelectGameModeGui;
import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static my.dw.gamemodeplugin.utils.GuiUtils.NAME_TO_BASE_GUI;

public class SelectGameModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            if (GuiUtils.currentGameMode != null) {
                player.sendMessage("Gamemode " + GuiUtils.currentGameMode.name() + " is currently being configured." +
                    "To continue that setup, type /setupgamemode. To end that setup, type /endgamemode.");
                player.closeInventory();
                return false;
            }

            NAME_TO_BASE_GUI.get(SelectGameModeGui.DEFAULT_NAME).openInventory(player);
            return true;
        }

        return false;
    }

}
