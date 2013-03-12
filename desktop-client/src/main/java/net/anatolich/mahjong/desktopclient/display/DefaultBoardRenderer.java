package net.anatolich.mahjong.desktopclient.display;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
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
 * Renders mahjong board.
 *
 * @author Dmytro Kovalchuk
 * @since 0.1
 * @version 1.0
 */
public class DefaultBoardRenderer implements BoardListener {

    private static final Logger logger = LoggerFactory.getLogger( DefaultBoardRenderer.class );
    private static final int TILE_WIDTH = 60;
    private static final int TILE_HEIGHT = 80;
    private int translateX;
    private int translateY;
    private Board board;
    private int height;
    private int width;
    private PieceRenderer pieceRenderer = new PieceRenderer( TILE_WIDTH, TILE_HEIGHT );
    private List<Piece> renderingQueue; // Contains pieces in order it should be rendered
    private final int boardWidth;
    private final int boardHeight;
    private final BoardComponent boardComponent;

    public DefaultBoardRenderer( BoardComponent canvas ) {
        this.boardComponent = canvas;
        this.board = getSession().getBoard();
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();
        Dimension boardSize = calculateBoardSize( board );
        boardWidth = ( int ) ( boardSize.getWidth() / 2 * TILE_WIDTH ) + 1;
        boardHeight = ( int ) ( boardSize.getHeight() / 2 * TILE_HEIGHT ) + 1;
        queuePiecesForRendering();
    }

    public void draw( Graphics2D g ) {
        translateX = ( boardComponent.getWidth() - boardWidth ) / 2;
        translateY = ( boardComponent.getHeight() - boardHeight ) / 2;
        clearBoard( g );
        g.translate( translateX, translateY );
        for ( Piece piece : renderingQueue ) {
            paintPiece( piece, g );
        }
        g.dispose();
    }

    private void clearBoard( Graphics2D g ) {
        Graphics2D g2 = ( Graphics2D ) g.create();
        g2.setBackground( boardComponent.getBackground() );
        logger.debug( "Cleaning up board {}x{}", width, height );
        g2.clearRect( 0, 0, width, height );
        g2.dispose();
    }

    private Shape createPieceClip( Coordinates coordinates ) {
        final Coordinates[] touchingPiecesCoordinates = new Coordinates[]{ coordinates.translate( -2, -1, 0 ), coordinates.translate( -2, 0, 0 ), coordinates.translate( -2, 1, 0 ), coordinates.translate( -2, 2, 0 ), coordinates.translate( -1, 2, 0 ), coordinates.translate( 0, 2, 0 ) };
		int baseX = calculateX( coordinates );
		int baseY = calculateY( coordinates );
		final AffineTransform baseTranslation = AffineTransform.getTranslateInstance( baseX, baseY );
		final Area baseClippingPath = new Area( baseTranslation.createTransformedShape( pieceRenderer.getClip() ) );

        for ( Coordinates coords : touchingPiecesCoordinates ) {
            if ( board.getPieceAt( coords ) == null ) {
                continue;
            }
			int touchingX = calculateX( coords );
			int touchingY = calculateY( coords );

			final AffineTransform touchingTranslation = AffineTransform.getTranslateInstance( touchingX, touchingY );

			Shape touchingOutline = touchingTranslation.createTransformedShape( pieceRenderer.getClip() );
            baseClippingPath.subtract( new Area( touchingOutline ) );
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
        final int x = calculateX( coords );
        final int y = calculateY( coords );
        Graphics2D pieceGraphics = ( Graphics2D ) g.create();
        Shape clip = createPieceClip( piece.getCoordinates() );
        pieceGraphics.setClip( clip );
		pieceGraphics.translate( x, y );
        pieceRenderer.paint( piece.getTile(), isSelected( piece ), isHighlighted( piece ), pieceGraphics );
        pieceGraphics.dispose();
    }

    private void queuePiecesForRendering() {
        renderingQueue = new ArrayList<>( board.getAllPieces() );
        Collections.sort( renderingQueue, new LayerPieceComparator() );
    }

    private boolean isSelected( Piece piece ) {
        return getSession().getPickedPieces().contains( piece );
    }

    @Override
    public void boardChanged( BoardEvent evt ) {
        queuePiecesForRendering();
    }

    private Dimension calculateBoardSize( Board board ) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for ( Piece piece : board.getAllPieces() ) {
            Coordinates c = piece.getCoordinates();
            final int x = c.getX();
            final int y = c.getY();
            if ( x < minX ) {
                minX = x;
            }
            if ( x > maxX ) {
                maxX = x;
            }
            if ( y < minY ) {
                minY = y;
            }
            if ( y > maxY ) {
                maxY = y;
            }
        }
        final int boardWidth = maxX - minX;
        final int boardHeight = maxY - minY;
        return new Dimension( boardWidth, boardHeight );
    }

    public Piece getPieceAt( Point2D point ) {
        List<Piece> pieces = new ArrayList<>( board.getAllPieces() );
        Collections.sort( pieces, Collections.reverseOrder( new LayerPieceComparator() ) );
        for ( Piece piece : pieces ) {
            Coordinates c = piece.getCoordinates();
            if ( pieceRenderer.getFace( calculateX( c ) + translateX, calculateY( c ) + translateY ).contains( point ) ) {

                return piece;
            }
        }
        return null;
    }

    /**
     * @return the session
     */
    private GameSession getSession() {
        return boardComponent.getGameSession();
    }

    private boolean isHighlighted( Piece piece ) {
        return boardComponent.getHighlightedPieces().contains( piece );
    }

    private static class LayerPieceComparator implements Comparator<Piece> {

        @Override
        public int compare( Piece o1, Piece o2 ) {
            return o1.getCoordinates().getLayer() - o2.getCoordinates().getLayer();
        }
    }
}
