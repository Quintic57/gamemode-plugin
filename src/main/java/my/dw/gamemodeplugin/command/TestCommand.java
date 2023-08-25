package my.dw.gamemodeplugin.command;

import my.dw.gamemodeplugin.model.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            player.sendMessage("Team Names: "
                + GameMode.DEATHMATCH.getCurrentConfiguration().getTeams()
                .stream().map(team -> team.getColor() + team.getName()).collect(Collectors.joining(", ")));
            return true;
        }

        return false;
    }

}
