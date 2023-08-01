package my.dw.gamemodeplugin.command;

import my.dw.gamemodeplugin.model.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            player.sendMessage("Number of Teams: " + GameMode.DEATHMATCH.getHandler().getCurrentConfiguration().getNumberOfTeams());
            return true;
        }

        return false;
    }

}
