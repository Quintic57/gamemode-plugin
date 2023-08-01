package my.dw.gamemodeplugin.model.handlers;

import my.dw.gamemodeplugin.model.GameMode;
import my.dw.gamemodeplugin.model.GameModeConfiguration;
import my.dw.gamemodeplugin.model.GameModeHandler;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class CaptureTheFlagHandler extends GameModeHandler {

    public CaptureTheFlagHandler(final GameModeConfiguration defaultConfiguration) {
        super(defaultConfiguration);
    }

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
