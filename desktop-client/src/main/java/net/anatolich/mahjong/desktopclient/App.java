package net.anatolich.mahjong.desktopclient;

import java.util.List;
import javax.swing.SwingUtilities;
import net.anatolich.mahjong.desktopclient.display.GameWindow;
import net.anatolich.mahjong.game.Game;
import net.anatolich.mahjong.game.GameFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application runner
 *
 */
public class App implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) {
        SwingUtilities.invokeLater(new App());
    }

    private GameWindow gameWindow;

    @Override
    public void run() {

        Application context = Application.getContext();
        context.setDevMode(true);

        gameWindow = new GameWindow();

        final GameFactory gameFactory = new GameFactory();
        List<Game> availableGames = gameFactory.availableGames();

        for ( Game game : availableGames ) {
            logger.debug("Loading game: {}", game.getName());
        }

        gameWindow.setAvailableGames(availableGames);

        gameWindow.setVisible(true);

    }

    public void repaint() {
        gameWindow.repaint();
    }

    public void close() {
        gameWindow.dispose();
    }
}
