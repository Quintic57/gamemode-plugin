package my.dw.gamemodeplugin.utils;

import my.dw.gamemodeplugin.GameModePlugin;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;

import java.util.List;

public class GameModeUtils {

    public static final List<ChatColor> TEAM_COLOR_OPTIONS;
    public static final int MAX_NUMBER_OF_TEAMS;
    public static final NamespacedKey CURRENT_GAME_MODE_KEY;

    // TODO: Fix random color issue
    static {
        TEAM_COLOR_OPTIONS = List.of(
            ChatColor.AQUA,
            ChatColor.BLUE,
            ChatColor.DARK_AQUA,
            ChatColor.DARK_BLUE,
            ChatColor.DARK_GRAY,
            ChatColor.DARK_GREEN,
            ChatColor.DARK_PURPLE,
            ChatColor.DARK_RED,
            ChatColor.GOLD,
            ChatColor.GRAY,
            ChatColor.GREEN,
            ChatColor.LIGHT_PURPLE,
            ChatColor.RED,
            ChatColor.WHITE,
            ChatColor.YELLOW
        );
        MAX_NUMBER_OF_TEAMS = TEAM_COLOR_OPTIONS.size();
        CURRENT_GAME_MODE_KEY = new NamespacedKey(GameModePlugin.getPlugin(), "current-game-mode");
    }

}
