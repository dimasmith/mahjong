package net.anatolich.mahjong.desktopclient;

import net.anatolich.mahjong.desktopclient.dev.CoordinateHighlightListener;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
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
    private IsometricBoard board = new IsometricBoard();
    private JFrame canvas = new JFrame();

    @Override
    public void run() {

        final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice defaultScreenDevice = ge.getDefaultScreenDevice();
        boolean fullScreenSupported = defaultScreenDevice.isFullScreenSupported();

        if ( fullScreenSupported ) {
            logger.debug("Full screen mode supported. Starting in fullscreen");
            canvas.setUndecorated(true);
            canvas.setFocusable(true);
            canvas.setIgnoreRepaint(true);
            defaultScreenDevice.setFullScreenWindow(canvas);
            canvas.setVisible(true);
            repaint();

            logger.debug("Registering listeners");

            canvas.addKeyListener(new CloseKeyListener(canvas, this));
            canvas.addMouseMotionListener(new CoordinateHighlightListener(( Graphics2D ) canvas.getGraphics()));

            logger.debug("Main window initialized");
        } else {
            logger.debug("No windowed mode support for now. Exiting");
        }

    }

    private void repaint() {
        Graphics2D g2 = ( Graphics2D ) canvas.getGraphics();
        board.setHeight(canvas.getHeight());
        board.setWidth(canvas.getWidth());
        board.draw(g2);
        logger.debug("Repainted");
    }

    private static class CloseKeyListener extends KeyAdapter {

        private final Window window;
        private final App app;

        public CloseKeyListener( Window window, App app ) {
            this.window = window;
            this.app = app;
        }

        @Override
        public void keyTyped( KeyEvent e ) {
            logger.debug("keyTyped: {}", e);
            if ( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
                window.dispose();
            }

        }

        @Override
        public void keyPressed( KeyEvent e ) {
            logger.debug("keyPressed: {}", e);
            if ( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
                window.dispose();
            }

            if ( e.getKeyCode() == KeyEvent.VK_F5 ) {
                logger.debug("Refreshing screen");
                app.repaint();
            }
        }
    }
}
