package my.dw.gamemodeplugin.command;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.selectgamemode.SelectGameModeGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static my.dw.gamemodeplugin.utils.GuiUtils.NAME_TO_UNIQUE_GUI;

public class EndGameModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                player.sendMessage("Expected exactly 1 argument, but got 0.");
                return false;
            }

            final String gameMode = args[0].toUpperCase();
            try {
                GameMode.valueOf(gameMode).getHandler().after();
            } catch (final IllegalArgumentException e) {
                player.sendMessage("Provided game mode " + gameMode + " is not a valid game mode");
                return false;
            }

            return true;
        }

        return false;
    }

}
