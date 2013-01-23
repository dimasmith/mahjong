package net.anatolich.mahjong.desktopclient.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.anatolich.mahjong.desktopclient.assets.PiecesTileMap;
import net.anatolich.mahjong.game.Board;
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
    private final PiecesTileMap tileMap;
    private final CoordinateMapper coordinateMapper = new CoordinateMapper(10);
    private double clickX, clickY;

    public IsometricBoardRenderer( Board board ) {
        this.board = board;
        this.width = 16;
        this.height = 16;
        this.tileMap = new PiecesTileMap();
    }

    public void draw( Graphics2D g ) {

        clearBoard(g);

        List<Piece> pieces = new ArrayList<>(board.getAllPieces());
        Collections.sort(pieces, new IsometricCoordinateComparator());
        final double translateX = ( width - coordinateMapper.getLengthAlongX(30) ) / 2;
        final double translateY = ( height - coordinateMapper.getLengthAlongY(16) ) / 2;

        g.translate(translateX, translateY);
        final Point2D.Double clickPoint = new Point2D.Double(clickX - translateX, clickY - translateY);
        g.fill(new Ellipse2D.Double(clickPoint.getX() - 5, clickPoint.getY() - 5, 10, 10));

        TileShape highlightedShape = null;

        final List<TileShape> shapes = new ArrayList<>();

        for ( Piece piece : pieces ) {
            final TileShape tileShape = new TileShape(piece);
            tileShape.setPiecesTileMap(tileMap);
            if ( tileShape.containsPoint(clickPoint) ) {
                if ( highlightedShape == null || highlightedShape.getPiece().getCoordinates().getLayer() < tileShape.getPiece().getCoordinates().getLayer() ) {
                    highlightedShape = tileShape;
                }
            }
            shapes.add(tileShape);
        }

        if ( highlightedShape != null ) {
            highlightedShape.setHighlighted(true);
        }

        for ( TileShape tileShape : shapes ) {
            tileShape.draw(g);
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

    public void clickOn( double x, double y ) {
        this.clickX = x;
        this.clickY = y;
        logger.debug("Cliked at ({}, {})", x, y);
    }
}
