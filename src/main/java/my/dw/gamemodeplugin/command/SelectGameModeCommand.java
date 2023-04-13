package my.dw.gamemodeplugin.command;

import my.dw.gamemodeplugin.ui.SelectGameModeGui;
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
            NAME_TO_BASE_GUI.get(SelectGameModeGui.DEFAULT_NAME).openInventory(player);

            return true;
        }

        return false;
    }

}
