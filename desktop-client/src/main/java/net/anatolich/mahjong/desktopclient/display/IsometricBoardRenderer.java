package net.anatolich.mahjong.desktopclient.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Isometric view of mahjong board.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class IsometricBoardRenderer {

    private static final Logger logger = LoggerFactory.getLogger(IsometricBoardRenderer.class);
    private Board board;
    private int height, width;

    public IsometricBoardRenderer( int width, int height ) {
        this.height = height;
        this.width = width;
    }

    public IsometricBoardRenderer( Board board ) {
        this.board = board;
        this.width = 16;
        this.height = 16;
    }

    public void draw( Graphics2D g ) {

        clearBoard(g);

        List<Piece> pieces = new ArrayList<>(board.getAllPieces());
        Collections.sort(pieces, new IsometricCoordinateComparator());

        for ( Piece piece : pieces ) {
            new TileShape(piece).draw(g);
        }

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard( Board board ) {
        this.board = board;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight( int height ) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth( int width ) {
        this.width = width;
    }

    private void clearBoard( Graphics2D g ) {
        Graphics2D g2 = ( Graphics2D ) g.create();

        g2.setBackground(Color.WHITE);
        logger.debug("Cleaning up board {}x{}", width, height);
        g2.clearRect(0, 0, width, height);

        g2.dispose();
    }
}
