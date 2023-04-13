package my.dw.gamemodeplugin.command;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.SelectGameModeGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EndGameModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            GameMode.after();

            return true;
        }

        return false;
    }

}
