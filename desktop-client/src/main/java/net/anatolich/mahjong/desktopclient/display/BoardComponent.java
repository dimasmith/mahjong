package net.anatolich.mahjong.desktopclient.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import javax.swing.JComponent;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.BoardListener;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.GameSession;
import net.anatolich.mahjong.game.Piece;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class BoardComponent extends JComponent {

    private GameSession session;
    private Board board;
    private BoardView renderer;

    public BoardComponent() {
        this.session = new DummyGameSession();
        this.renderer = new BoardView(session);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed( MouseEvent e ) {
                if ( e.getButton() == MouseEvent.BUTTON1 ) {
                    renderer.clickOn(e.getX(), e.getY());
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent( Graphics g ) {
        renderer.setWidth(getWidth());
        renderer.setHeight(getHeight());

        renderer.draw(( Graphics2D ) g);

    }

    public Board getBoard() {
        return session.getBoard();
    }

    public GameSession getGameSession() {
        return session;
    }

    public void setGameSession( GameSession game ) {
        this.session = game;
        this.board = game.getBoard();
        this.renderer = new BoardView(game);
        board.addChangeListener(renderer);
    }

    private static class DummyGameSession implements GameSession {

        private static final EmptyBoard EMPTY_BOARD = new EmptyBoard();

        @Override
        public Board getBoard() {
            return EMPTY_BOARD;
        }

        @Override
        public List<Piece> getPickedPieces() {
            return Collections.EMPTY_LIST;
        }

        @Override
        public boolean wasMoveCompleted() {
            return false;
        }

        @Override
        public void pickPieceAt( Coordinates coordinates ) {
        }

        @Override
        public boolean hasMoreMoves() {
            return false;
        }

        @Override
        public boolean isGameEnded() {
            return false;
        }

    }

    private static class EmptyBoard implements Board {

        @Override
        public List<Piece> getAllPieces() {
            return Collections.EMPTY_LIST;
        }

        @Override
        public Piece getTopmostPieceAt( int x, int y ) {
            return null;
        }

        @Override
        public Piece getPieceAt( Coordinates coordinates ) {
            return null;
        }

        @Override
        public void addChangeListener( BoardListener listener ) {
        }

        @Override
        public void removeChangeListener( BoardListener listener ) {
        }
    }
}
