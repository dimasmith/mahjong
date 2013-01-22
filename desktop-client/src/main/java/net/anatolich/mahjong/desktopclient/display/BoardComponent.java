package net.anatolich.mahjong.desktopclient.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.List;
import javax.swing.JComponent;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class BoardComponent extends JComponent {

    private Board board;

    public BoardComponent() {
        this.board = new EmptyBoard();
    }

    @Override
    protected void paintComponent( Graphics g ) {
        IsometricBoardRenderer isometricBoard = new IsometricBoardRenderer(board);
        isometricBoard.setWidth(getWidth());
        isometricBoard.setHeight(getHeight());

        isometricBoard.draw(( Graphics2D ) g);

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard( Board board ) {
        this.board = board;
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

    }
}
