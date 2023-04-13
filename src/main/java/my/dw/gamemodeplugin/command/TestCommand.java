package my.dw.gamemodeplugin.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static my.dw.gamemodeplugin.utils.GuiUtils.INVENTORY_TO_GUI;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
//            INVENTORY_TO_GUI.values().forEach(inventory -> System.out.println(inventory.getGuiName()));
            System.out.println(Bukkit.getScoreboardManager().getMainScoreboard().getTeams());
            return true;
        }

        return false;
    }

}
