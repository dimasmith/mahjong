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
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
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

    public PieceRenderer( int width, int height ) {
        this.width = width;
        this.height = height;
        this.bevel = width / 4;
        this.tileMap = new PiecesTileMap();
    }

    public Shape getClip( int x, int y ) {
        final Area outline = new Area();
        final int clipX = x;
        final int clipY = y;
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

        outline.add( new Area( face ) );
        outline.add( new Area( footprint ) );
        outline.add( new Area( topLeftCorner ) );
        outline.add( new Area( bottomRightCorner ) );

        return outline;
    }

    public Shape getOutline( int x, int y ) {
        final Area outline = new Area();
        Rectangle2D face = getFace( x, y );
        Rectangle2D footprint = new Rectangle2D.Double( x - bevel, y + bevel, width, height );

        Path2D topLeftCorner = new Path2D.Double();
        topLeftCorner.moveTo( x, y );
        topLeftCorner.lineTo( x, y + bevel );
        topLeftCorner.lineTo( x - bevel, y + bevel );
        topLeftCorner.closePath();

        Path2D bottomRightCorner = new Path2D.Double();
        topLeftCorner.moveTo( x + width, y + height );
        topLeftCorner.lineTo( x + width - bevel, y + height );
        topLeftCorner.lineTo( x + width - bevel, y + height + bevel );
        topLeftCorner.closePath();

        outline.add( new Area( face ) );
        outline.add( new Area( footprint ) );
        outline.add( new Area( topLeftCorner ) );
        outline.add( new Area( bottomRightCorner ) );

        return outline;
    }

    public void paint( int x, int y, boolean selected, boolean highlighted, Tile tile, Graphics2D g2 ) {
        final Shape outline = getOutline( x, y );

        g2.setColor( outlineFillColor );
        g2.fill( outline );

        g2.setColor( outlineColor );
        g2.draw( outline );

        if ( selected ) {
            g2.setColor( faceSelectedColor );
        } else if ( highlighted ) {
            g2.setColor( faceHighlihgtedColor );
        } else {
            g2.setColor( faceFillColor );
        }

        final Rectangle2D face = getFace( x, y );
        g2.fill( face );

        g2.setColor( outlineColor );
        g2.draw( face );

        g2.drawImage( tileMap.getPieceImage( tile ), x, y, null );
    }

    public Rectangle2D getFace( int x, int y ) {
        return new Rectangle2D.Double( x, y, width, height );
    }
}
