package my.dw.gamemodeplugin.command;

import static my.dw.gamemodeplugin.utils.GuiUtils.NAME_TO_UNIQUE_GUI;

import my.dw.gamemodeplugin.ui.selectgamemode.SelectGameModeGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SelectGameModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            NAME_TO_UNIQUE_GUI.get(SelectGameModeGui.NAME).openInventory(player);
            return true;
        }

        return false;
    }

}
