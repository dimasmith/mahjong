package net.anatolich.mahjong.desktopclient.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import net.anatolich.mahjong.desktopclient.assets.PiecesTileMap;
import net.anatolich.mahjong.game.Tile;

/**
 * Renders piece and provides information about piece interaction area and
 * clipping path.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 * @since 0.2
 * @version 1.0
 */
public class PieceRenderer {

    private final int width;
    private final int height;
    private final int bevel;
    private Color outlineColor = Color.BLACK;
    private Color outlineFillColor = Color.WHITE;
    private Color faceFillColor = Color.YELLOW;
    private Color faceSelectedColor = Color.YELLOW.darker();
    private Color faceHighlihgtedColor = Color.BLUE.brighter().brighter();
    private final PiecesTileMap tileMap;
    private Area clip;
    private Area outline;

    public PieceRenderer( int width, int height ) {
        this.width = width;
        this.height = height;
        this.bevel = width / 4;
        this.tileMap = new PiecesTileMap();
    }

    /**
     * Provides default clipping path for piece that have no neighbors.
     * Clipping path must be furthers processed taking into account pieces that
     * are neighbors of currently rendered one.
     *
     * Clipping path is created for piece that is situated at (0,0) coordinates.
     * It should be translated in order to use on board.
     *
     * @return default clipping path for piece shape.
     */
    public Shape getClip() {
        if ( clip == null ) {
            clip = new Area();
            final int clipX = 0;
            final int clipY = 0;
            final int clipWidth = width + 1;
            final int clipHeight = height + 1;
            final int clipBevel = bevel;
            Rectangle2D face = new Rectangle2D.Double( clipX, clipY, clipWidth, clipHeight );
            Rectangle2D footprint = new Rectangle2D.Double( clipX - clipBevel, clipY + clipBevel, clipWidth, clipHeight );

            Path2D topLeftCorner = new Path2D.Double();
            topLeftCorner.moveTo( clipX, clipY );
            topLeftCorner.lineTo( clipX, clipY + clipBevel );
            topLeftCorner.lineTo( clipX - clipBevel, clipY + clipBevel );
            topLeftCorner.closePath();

            Path2D bottomRightCorner = new Path2D.Double();
            topLeftCorner.moveTo( clipX + clipWidth, clipY + clipHeight );
            topLeftCorner.lineTo( clipX + clipWidth - clipBevel, clipY + clipHeight );
            topLeftCorner.lineTo( clipX + clipWidth - clipBevel, clipY + clipHeight + clipBevel );
            topLeftCorner.closePath();

            clip.add( new Area( face ) );
            clip.add( new Area( footprint ) );
            clip.add( new Area( topLeftCorner ) );
            clip.add( new Area( bottomRightCorner ) );
        }
        return clip;
    }

    private Shape getOutline() {
        if ( outline == null ) {
            outline = new Area();
            Rectangle2D face = getFace( 0, 0 );
            Rectangle2D footprint = new Rectangle2D.Double( -bevel, bevel, width, height );

            Path2D topLeftCorner = new Path2D.Double();
            topLeftCorner.moveTo( 0, 0 );
            topLeftCorner.lineTo( 0, bevel );
            topLeftCorner.lineTo( -bevel, bevel );
            topLeftCorner.closePath();

            Path2D bottomRightCorner = new Path2D.Double();
            topLeftCorner.moveTo( width, height );
            topLeftCorner.lineTo( width - bevel, height );
            topLeftCorner.lineTo( width - bevel, height + bevel );
            topLeftCorner.closePath();

            outline.add( new Area( face ) );
            outline.add( new Area( footprint ) );
            outline.add( new Area( topLeftCorner ) );
            outline.add( new Area( bottomRightCorner ) );
        }
        return outline;
    }

    /**
     * Paints shape of piece. Shape is painted in (0,0) coordinates and needs to
     * be translated to it's real position.
     *
     * @param tile        type of piece.
     * @param selected    whether piece is currently selected by player or no.
     * @param highlighted whether piece is highlighted (e.g. for showing hint).
     * @param g2          graphics context
     */
    public void paint( Tile tile, boolean selected, boolean highlighted, Graphics2D g2 ) {
        final Shape shapeOutline = getOutline();

        g2.setColor( outlineFillColor );
        g2.fill( shapeOutline );

        g2.setColor( outlineColor );
        g2.draw( shapeOutline );

        if ( selected ) {
            g2.setColor( faceSelectedColor );
        } else if ( highlighted ) {
            g2.setColor( faceHighlihgtedColor );
        } else {
            g2.setColor( faceFillColor );
        }

        final Rectangle2D face = getFace( 0, 0 );
        g2.fill( face );

        g2.setColor( outlineColor );
        g2.draw( face );

        g2.drawImage( tileMap.getPieceImage( tile ), 0, 0, null );
    }

    /**
     * Returns rectangle that encloses clickable area of piece. Following code
     * detects whether piece was clicked or no:
     * <pre>
     * <code>
     *         boolean isClicked = getFace(graphicX, graphicY).contains(clickPoint); // clickPoint should be of type Point2D
     * </code>
     * </pre>
     *
     * @param x graphical coordinate of piece.
     * @param y graphical coordinate of piece.
     *
     * @return rectangle enclosing clickable area.
     */
    public Rectangle2D getFace( int x, int y ) {
        return new Rectangle2D.Double( x, y, width, height );
    }
}
