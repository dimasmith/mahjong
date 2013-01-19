package net.anatolich.mahjong.desktopclient;

import javax.swing.SwingUtilities;
import net.anatolich.mahjong.desktopclient.display.GameWindow;
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
    private GameWindow gameWindow = new GameWindow();

    @Override
    public void run() {
        gameWindow.setVisible(true);

    }

    public void repaint() {
        gameWindow.repaint();
    }

    public void close() {
        gameWindow.dispose();
    }
}
