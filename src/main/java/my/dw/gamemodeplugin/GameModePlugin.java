package my.dw.gamemodeplugin;

import my.dw.gamemodeplugin.command.EndGameModeCommand;
import my.dw.gamemodeplugin.command.SelectGameModeCommand;
import my.dw.gamemodeplugin.command.TestCommand;
import my.dw.gamemodeplugin.listener.InventoryGuiListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class GameModePlugin extends JavaPlugin {

    private static GameModePlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        // Plugin startup logic
        getCommand("endgamemode").setExecutor(new EndGameModeCommand());
        getCommand("selectgamemode").setExecutor(new SelectGameModeCommand());
        getCommand("test").setExecutor(new TestCommand());
        getServer().getPluginManager().registerEvents(new InventoryGuiListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static GameModePlugin getPlugin() {
        return plugin;
    }

}
