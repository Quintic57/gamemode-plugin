package my.dw.gamemodeplugin.command;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.ui.selectgamemode.SelectGameModeGui;
import my.dw.gamemodeplugin.utils.GameModeUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import static my.dw.gamemodeplugin.utils.GuiUtils.NAME_TO_UNIQUE_GUI;

public class EndGameModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            final String gameMode = player.getWorld()
                .getPersistentDataContainer()
                .get(GameModeUtils.CURRENT_GAME_MODE_KEY, PersistentDataType.STRING);
            try {
                GameMode.valueOf(gameMode).getHandler().after();
            } catch (final IllegalArgumentException e) {
                player.sendMessage("There is no active game mode");
                return false;
            }

            return true;
        }

        return false;
    }

}
