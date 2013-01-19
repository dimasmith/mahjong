package net.anatolich.mahjong.desktopclient.display;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class GameWindow extends JFrame {

    private JMenuBar menuBar;
    private JMenu settingsMenu;
    private JMenuItem settingsMenuItem;

    private JMenu gameMenu;
    private JMenuItem exitMenuItem;

    private BoardComponent boardComponent;

    public GameWindow() {
        setUp();
    }

    private void setUp() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        menuBar = new JMenuBar();

        gameMenu = new JMenu("Game");
        exitMenuItem = new JMenuItem(new ExitAction(this));
        gameMenu.add(exitMenuItem);
        menuBar.add(gameMenu);

        settingsMenu = new JMenu("Settings");
        settingsMenuItem = new JMenuItem("Settings...");
        settingsMenu.add(settingsMenuItem);
        menuBar.add(settingsMenu);

        boardComponent = new BoardComponent();
        add(boardComponent, BorderLayout.CENTER);

        setJMenuBar(menuBar);
        pack();
        setExtendedState(MAXIMIZED_BOTH);
    }

    private static class ExitAction extends AbstractAction {

        private final GameWindow gameWindow;

        public ExitAction(GameWindow gameWindow) {
            this.gameWindow = gameWindow;
            putValue(NAME, "Exit");
        }

        @Override
        public void actionPerformed( ActionEvent e ) {
            gameWindow.dispose();
        }


    }
}
