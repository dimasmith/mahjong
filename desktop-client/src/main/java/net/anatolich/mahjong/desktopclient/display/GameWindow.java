package net.anatolich.mahjong.desktopclient.display;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import net.anatolich.mahjong.desktopclient.Application;
import net.anatolich.mahjong.game.AvailableMove;
import net.anatolich.mahjong.game.Game;
import net.anatolich.mahjong.game.GameSession;
import net.anatolich.mahjong.game.capabilities.Hints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class GameWindow extends JFrame {

    private static final Logger log = LoggerFactory.getLogger(GameWindow.class);

    private JMenuBar menuBar;
    private JMenu settingsMenu;
    private JMenuItem settingsMenuItem;
    private JMenu devMenu;
    private JMenuItem repaintMenuItem;
    private JMenu gameMenu;
    private JMenuItem exitMenuItem;
    private BoardComponent boardComponent;
    private List<JMenuItem> dynamicItems = new ArrayList<>();

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
        final boolean devMode = Application.getContext().isDevMode();

        final String title = ( devMode ) ? "Tile Game - DEV MODE" : "Tile Game";
        setTitle(title);

        if ( devMode ) {
            devMenu = new JMenu("Development");
            repaintMenuItem = new JMenuItem(new DevRepaintAction());
            devMenu.add(repaintMenuItem);
            menuBar.add(devMenu);
        }

        pack();
        setExtendedState(MAXIMIZED_BOTH);
    }

    void startGame( GameSession session ) {
        boardComponent.setGameSession(session);
        boardComponent.repaint();
    }

    public void setAvailableGames( List<Game> availableGames ) {
        for ( Game game : availableGames ) {
            final PlayGameAction playGameAction = new PlayGameAction(game);
            gameMenu.insert(playGameAction, 0);
        }
    }

    private void reloadDynamicItems(GameSession gameSession) {
        for (JMenuItem item : dynamicItems) {
            gameMenu.remove(item);
        }
        if(gameSession.capabilities().supports(Hints.class)){
            Hints hints = gameSession.capabilities().get(Hints.class);
            ShowHintsAction showHintsAction = new ShowHintsAction(hints);
            JMenuItem displayHintItem = new JMenuItem(showHintsAction);
            addDynamicItem(displayHintItem);
        }
    }

    private void addDynamicItem(JMenuItem displayHintItem) {
        dynamicItems.add(displayHintItem);
        gameMenu.add(displayHintItem);
    }

    private static class ExitAction extends AbstractAction {

        private final GameWindow gameWindow;

        public ExitAction( GameWindow gameWindow ) {
            this.gameWindow = gameWindow;
            putValue(NAME, "Exit");
        }

        @Override
        public void actionPerformed( ActionEvent e ) {
            gameWindow.dispose();
        }
    }

    private class PlayGameAction extends AbstractAction {

        private final Game game;

        public PlayGameAction( Game game ) {
            this.game = game;
            putValue(NAME, String.format("Play %s", game.getName()));
        }

        @Override
        public void actionPerformed( ActionEvent e ) {
            final GameSession gameSession = game.startGame();
            startGame(gameSession);
            reloadDynamicItems(gameSession);
        }
    }

    private class ShowHintsAction extends AbstractAction {
        private final Hints hints;

        private ShowHintsAction(Hints hints) {
            super();
            this.hints = hints;
            putValue(NAME, "Show hint");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (hints.hasHints()){
                final AvailableMove hint = hints.nextHint();
                boardComponent.clearHighlighting();
                boardComponent.highlight(hint.getStartPiece());
                boardComponent.highlight(hint.getEndPiece());
                boardComponent.repaint();
            }
        }

    }

    private class DevRepaintAction extends AbstractAction {

        public DevRepaintAction() {
            putValue(NAME, "Repaint");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("pressed F5"));
        }

        @Override
        public void actionPerformed( ActionEvent ae ) {
            boardComponent.repaint();
        }
    }
}
