package my.dw.gamemodeplugin;

import my.dw.gamemodeplugin.command.EndGameModeCommand;
import my.dw.gamemodeplugin.command.SelectGameModeCommand;
import my.dw.gamemodeplugin.listener.GuiInventoryListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class GameModePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("endgamemode").setExecutor(new EndGameModeCommand());
        getCommand("selectgamemode").setExecutor(new SelectGameModeCommand());
        getServer().getPluginManager().registerEvents(new GuiInventoryListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
