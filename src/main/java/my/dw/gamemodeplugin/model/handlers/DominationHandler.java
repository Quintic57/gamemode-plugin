package my.dw.gamemodeplugin.model.handlers;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.model.GameModeHandler;
import my.dw.gamemodeplugin.utils.GameModeUtils;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scoreboard.Score;

public class DominationHandler extends GameModeHandler {

    public DominationHandler() {
        super();
    }

    @Override
    public void before(final Player gmm) {

    }

    @Override
    public void startGame(final Player player) {
        player.getWorld()
            .getPersistentDataContainer()
            .set(GameModeUtils.CURRENT_GAME_MODE_KEY, PersistentDataType.STRING, GameMode.DOMINATION.name());
    }

    @Override
    public void modifyScore(final Score currentScore) {

    }

    @Override
    public void after() {

    }

}
