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
import net.anatolich.mahjong.game.Piece;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class BoardComponent extends JComponent {

    private Board board;
    private BoardView renderer;

    public BoardComponent() {
        this.board = new EmptyBoard();
        this.renderer = new BoardView(board);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked( MouseEvent e ) {
                renderer.clickOn(e.getX(), e.getY());
                repaint();
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
        return board;
    }

    public void setBoard( Board board ) {
        this.board = board;
        this.renderer = new BoardView(board);
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
