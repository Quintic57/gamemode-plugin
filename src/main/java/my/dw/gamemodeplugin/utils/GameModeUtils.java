package my.dw.gamemodeplugin.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class GameModeUtils {

    public static final List<ChatColor> TEAM_COLOR_OPTIONS;
    public static final int MAX_NUMBER_OF_TEAMS;

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
    }

}
