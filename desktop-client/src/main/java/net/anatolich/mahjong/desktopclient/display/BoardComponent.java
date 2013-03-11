package net.anatolich.mahjong.desktopclient.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.BoardListener;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.GameEvent;
import net.anatolich.mahjong.game.GameSession;
import net.anatolich.mahjong.game.GameSessionListener;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.capabilities.Capabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class BoardComponent extends JComponent implements GameSessionListener {

    private static final Logger log = LoggerFactory.getLogger( BoardComponent.class );
    private GameSession session;
    private Board board;
    private DefaultBoardRenderer renderer;
    private Set<Piece> highlightedPieces;

    public BoardComponent() {
        highlightedPieces = new HashSet<>();
        this.session = new DummyGameSession();
        this.renderer = new DefaultBoardRenderer( this );

        addMouseListener( new ComponentMouseListener() );
    }

    @Override
    protected void paintComponent( Graphics g ) {
        renderer.draw( ( Graphics2D ) g );

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
        this.renderer = new DefaultBoardRenderer( this );
        board.addChangeListener( renderer );
        session.addListener( this );
    }

    @Override
    public void pickedPiecesChanged( GameEvent event ) {
        repaint();
    }

    @Override
    public void turnCompleted( GameEvent event ) {
        clearHighlighting();
        repaint();
    }

    @Override
    public void noMovesLeft() {
        JOptionPane.showMessageDialog( this, "No moves left in this game" );
    }

    @Override
    public void gameWon() {
        JOptionPane.showMessageDialog( this, "Congratulations! You have won!" );
    }

    void clearHighlighting() {
        highlightedPieces.clear();
    }

    void highlight( Piece piece ) {
        highlightedPieces.add( piece );
    }

    Set<Piece> getHighlightedPieces() {
        return Collections.unmodifiableSet( highlightedPieces );
    }

    private static class DummyGameSession implements GameSession {

        private static final EmptyBoard EMPTY_BOARD = new EmptyBoard();
        private final CapabilitiesImpl dummyCapabilities = new CapabilitiesImpl();

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

        @Override
        public void addListener( GameSessionListener listener ) {
        }

        @Override
        public void removeListener( GameSessionListener listener ) {
        }

        @Override
        public Capabilities capabilities() {
            return dummyCapabilities;
        }

        private static class CapabilitiesImpl implements Capabilities {

            public CapabilitiesImpl() {
            }

            @Override
            public <T> T get( Class<T> capability ) {
                return null;
            }

            @Override
            public boolean supports( Class<?> capability ) {
                return false;
            }
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

    private class ComponentMouseListener extends MouseAdapter {

        public ComponentMouseListener() {
        }

        @Override
        public void mousePressed( MouseEvent e ) {
            if ( e.getButton() == MouseEvent.BUTTON1 ) {
                Point2D.Double clickPoint = new Point2D.Double( e.getX(), e.getY() );
                final Piece piece = renderer.getPieceAt( clickPoint );

                if ( piece != null ) {
                    session.pickPieceAt( piece.getCoordinates() );
                }
            }
        }
    }
}
