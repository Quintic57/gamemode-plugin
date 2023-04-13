package my.dw.gamemodeplugin.command;

import my.dw.gamemodeplugin.ui.SelectGameModeGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SelectGameModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            SelectGameModeGui.openInventory(player);

            return true;
        }

        return false;
    }

}
