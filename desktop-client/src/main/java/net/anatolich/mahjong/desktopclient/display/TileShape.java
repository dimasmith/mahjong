package net.anatolich.mahjong.desktopclient.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import net.anatolich.mahjong.desktopclient.assets.PiecesTileMap;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class TileShape {

    private final Piece piece;
    private final CoordinateMapper coordinateMapper = new CoordinateMapper(10);
    private Point2D a;
    private Point2D b;
    private Point2D c;
    private Point2D d;
    private Point2D e;
    private Point2D f;
    private final Coordinates baseCoordinates;
    private PiecesTileMap piecesTileMap;
    private boolean highlighted = false;
    private Rectangle2D.Double topPane;
    private Point2D edgePoint;
    private GeneralPath.Double footprintPath;
    private Line2D edgeLine;

    public TileShape( Piece piece ) {
        this.piece = piece;
        this.baseCoordinates = piece.getCoordinates();
        calculateCornerPoints();
        createFootprintPath();
        createEdgeLine();
        createTopPane();
    }

    public void draw( Graphics2D g ) {
        Graphics2D g2 = ( Graphics2D ) g.create();
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1.0f));


        g2.setColor(Color.WHITE);
        g2.fill(footprintPath);
        g2.setColor(Color.BLACK);
        g2.draw(footprintPath);
        g2.draw(edgeLine);
        g2.setColor(( highlighted ) ? Color.BLUE : Color.LIGHT_GRAY);
        g2.fill(topPane);
        g2.drawImage(piecesTileMap.getPieceImage(piece.getTile()), ( int ) b.getX(), ( int ) b.getY(), null);
        g2.setColor(Color.BLACK);
        g2.draw(topPane);
        g2.dispose();
    }

    public double getHeight() {
        return coordinateMapper.getLengthAlongY(2);
    }

    public double getWidth() {
        return coordinateMapper.getLengthAlongX(2);
    }

    public PiecesTileMap getPiecesTileMap() {
        return piecesTileMap;
    }

    public void setPiecesTileMap( PiecesTileMap piecesTileMap ) {
        this.piecesTileMap = piecesTileMap;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted( boolean highlighted ) {
        this.highlighted = highlighted;
    }

    public Piece getPiece() {
        return piece;
    }

    private void calculateCornerPoints() {
        final Coordinates aC = baseCoordinates;
        final Coordinates bC = aC.translate(0, 0, 1);
        final Coordinates cC = bC.translate(2, 0, 0);
        final Coordinates dC = cC.translate(0, 2, 0);
        final Coordinates eC = dC.translate(0, 0, -1);
        final Coordinates fC = eC.translate(-2, 0, 0);
        a = coordinateMapper.mapTo2D(aC);
        b = coordinateMapper.mapTo2D(bC);
        c = coordinateMapper.mapTo2D(cC);
        d = coordinateMapper.mapTo2D(dC);
        e = coordinateMapper.mapTo2D(eC);
        f = coordinateMapper.mapTo2D(fC);
        edgePoint = coordinateMapper.mapTo2D(fC.translate(0, 0, 1));
    }

    public boolean containsPoint(Point2D clickPoint){
        return topPane.contains(clickPoint);
    }

    private void createFootprintPath() {
        footprintPath = new Path2D.Double();
        footprintPath.moveTo(a.getX(), a.getY());
        footprintPath.lineTo(b.getX(), b.getY());
        footprintPath.lineTo(c.getX(), c.getY());
        footprintPath.lineTo(d.getX(), d.getY());
        footprintPath.lineTo(e.getX(), e.getY());
        footprintPath.lineTo(f.getX(), f.getY());
        footprintPath.closePath();
    }

    private void createEdgeLine() {
        edgeLine = new Line2D.Double(f, edgePoint);
    }

    private void createTopPane() {
        double tileWidth = coordinateMapper.getLengthAlongX(2);
        double tileHeight = coordinateMapper.getLengthAlongY(2);
        topPane = new Rectangle2D.Double(b.getX(), b.getY(), tileWidth, tileHeight);
    }

}
