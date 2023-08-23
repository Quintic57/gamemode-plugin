package my.dw.gamemodeplugin.model;

import lombok.Data;
import org.bukkit.ChatColor;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class GameModeTeam {

    private final String name;

    //TODO: make this configurable, add CustomizeTeamCommand
    private String displayName;

    //TODO: make this configurable, add CustomizeTeamCommand
    private ChatColor color;

    // TODO: May be smarter to store player objects instead of just UUID
    private UUID captain;

    // TODO: May be smarter to store player objects instead of just UUID
    private final Set<UUID> playerList;

    public GameModeTeam(final String name, final ChatColor color) {
        this.name = name;
        this.color = color;
        playerList = new LinkedHashSet<>();
    }

    public void reset() {
        displayName = "";
        captain = null;
        playerList.clear();
    }

}
