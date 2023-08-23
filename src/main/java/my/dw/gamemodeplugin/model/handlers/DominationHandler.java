package my.dw.gamemodeplugin.model.handlers;

import my.dw.gamemodeplugin.model.GameModeConfiguration;
import my.dw.gamemodeplugin.model.GameModeHandler;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

public class DominationHandler extends GameModeHandler {

    @Override
    public void before(final Player gmm) {

    }

    @Override
    public boolean startGame() {
        return false;
    }

    @Override
    public void modifyScore(final Score currentScore) {

    }

    @Override
    public void after() {

    }

}
