package net.anatolich.mahjong.desktopclient;

import net.anatolich.mahjong.desktopclient.display.IsometricBoard;
import net.anatolich.mahjong.desktopclient.dev.CoordinateHighlightListener;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
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
    private GraphicsDevice defaultScreenDevice;

    @Override
    public void run() {

        final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        defaultScreenDevice = ge.getDefaultScreenDevice();
        boolean fullScreenSupported = defaultScreenDevice.isFullScreenSupported();

        if ( fullScreenSupported ) {
            logger.debug("Full screen mode supported. Starting in fullscreen");
            canvas.setUndecorated(true);
            canvas.setFocusable(true);
            canvas.setIgnoreRepaint(true);
            defaultScreenDevice.setFullScreenWindow(canvas);
            canvas.setVisible(true);
            canvas.createBufferStrategy(2);
            repaint();

            logger.debug("Registering listeners");

            canvas.addKeyListener(new CloseKeyListener(this));
            canvas.addMouseMotionListener(new CoordinateHighlightListener(( Graphics2D ) canvas.getGraphics()));

            logger.debug("Main window initialized");
        } else {
            logger.debug("No windowed mode support for now. Exiting");
        }

    }

    private void repaint() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics2D g2 = ( Graphics2D ) bufferStrategy.getDrawGraphics();

        board.setHeight(canvas.getHeight());
        board.setWidth(canvas.getWidth());

        board.draw(g2);
        logger.debug("Drawed to back buffer");

        bufferStrategy.show();
        logger.debug("Back buffer rendered");

        g2.dispose();
        logger.debug("Graphics disposed");
    }

    public void close() {
        defaultScreenDevice.setFullScreenWindow(null);
        canvas.dispose();
    }

    private static class CloseKeyListener extends KeyAdapter {

        private final App app;

        public CloseKeyListener( App app ) {
            this.app = app;
        }

        @Override
        public void keyTyped( KeyEvent e ) {
            logger.debug("keyTyped: {}", e);
            if ( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
                app.close();
            }

        }

        @Override
        public void keyPressed( KeyEvent e ) {
            logger.debug("keyPressed: {}", e);
            if ( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
                app.close();
            }

            if ( e.getKeyCode() == KeyEvent.VK_F5 ) {
                logger.debug("Refreshing screen");
                app.repaint();
            }
        }
    }
}
