package my.dw.gamemodeplugin.command;

import my.dw.gamemodeplugin.utils.GuiUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EndGameModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            if (GuiUtils.currentGameMode == null) {
                player.sendMessage("There is no game mode currently active");
                return false;
            }

            GuiUtils.currentGameMode.getHandler().after();
            return true;
        }

        return false;
    }

}
