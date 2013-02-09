package net.anatolich.mahjong.desktopclient.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.BoardEvent;
import net.anatolich.mahjong.game.BoardListener;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.GameSession;
import net.anatolich.mahjong.game.Piece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Isometric view of mahjong board.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class BoardView implements BoardListener {

    private static final Logger logger = LoggerFactory.getLogger(BoardView.class);
    private static final int TILE_WIDTH = 60;
    private static final int TILE_HEIGHT = 80;
    private static int TRANSLATE_X = 400;
    private static int TRANSLATE_Y = 50;
    private GameSession session;
    private Board board;
    private int height, width;
    private PieceRenderer pieceRenderer = new PieceRenderer(TILE_WIDTH, TILE_HEIGHT);
    private List<Piece> renderingQueue; // Contains pieces in order it should be rendered

    public BoardView( GameSession session ) {
        this.session = session;
        this.board = session.getBoard();
        this.width = 16;
        this.height = 16;

        queuePiecesForRendering();
    }

    public void draw( Graphics2D g ) {

        clearBoard(g);

        g.translate(TRANSLATE_X, TRANSLATE_Y);

        for ( Piece piece : renderingQueue ) {
            paintPiece(piece, g);
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
        logger.debug("Cliked at ({}, {})", x, y);
        Point2D clickPoint = new Point2D.Double(x, y);

        List<Piece> pieces = new ArrayList<>(board.getAllPieces());
        Collections.sort(pieces, Collections.reverseOrder(new LayerPieceComparator()));

        for ( Piece piece : pieces ) {
            Coordinates c = piece.getCoordinates();
            if ( pieceRenderer.getFace(calculateX(c) + TRANSLATE_X, calculateY(c) + TRANSLATE_Y).contains(clickPoint) ) {
                session.pickPieceAt(c);
            }
        }

    }

    private Shape createPieceClip( Coordinates coordinates ) {
        final Coordinates[] touchingPiecesCoordinates = new Coordinates[]{
            coordinates.translate(-2, -1, 0),
            coordinates.translate(-2, 0, 0),
            coordinates.translate(-2, 1, 0),
            coordinates.translate(-2, 2, 0),
            coordinates.translate(-1, 2, 0),
            coordinates.translate(0, 2, 0) };

        final Area baseClippingPath = new Area(pieceRenderer.getClip(calculateX(coordinates), calculateY(coordinates)));
        for ( Coordinates coords : touchingPiecesCoordinates ) {
            if ( board.getPieceAt(coords) == null ) {
                continue;
            }

            Shape touchingOutline = pieceRenderer.getClip(calculateX(coords), calculateY(coords));
            baseClippingPath.subtract(new Area(touchingOutline));
        }

        return baseClippingPath;
    }

    private int calculateX( final Coordinates coords ) {
        final int layerShift = TILE_WIDTH / 4 * ( coords.getLayer() - 1 );
        return coords.getX() * TILE_WIDTH / 2 + layerShift;
    }

    private int calculateY( final Coordinates coords ) {
        final int layerShift = TILE_WIDTH / 4 * ( coords.getLayer() - 1 );
        return coords.getY() * TILE_HEIGHT / 2 - layerShift;
    }

    private void paintPiece( Piece piece, Graphics2D g ) {
        final Coordinates coords = piece.getCoordinates();
        final int x = calculateX(coords);
        final int y = calculateY(coords);

        Graphics2D pieceGraphics = ( Graphics2D ) g.create();
        Shape clip = createPieceClip(piece.getCoordinates());
        pieceGraphics.setClip(clip);

        pieceRenderer.paint(x, y, isSelected(piece), piece.getTile(), pieceGraphics);

        pieceGraphics.dispose();
    }

    private void queuePiecesForRendering() {
        renderingQueue = new ArrayList<>(board.getAllPieces());
        Collections.sort(renderingQueue, new LayerPieceComparator());
    }

    private boolean isSelected( Piece piece ) {
        return session.getPickedPieces().contains(piece);
    }

    @Override
    public void boardChanged( BoardEvent evt ) {
        queuePiecesForRendering();
    }

    private static class LayerPieceComparator implements Comparator<Piece> {

        @Override
        public int compare( Piece o1, Piece o2 ) {
            return o1.getCoordinates().getLayer() - o2.getCoordinates().getLayer();
        }
    }
}
