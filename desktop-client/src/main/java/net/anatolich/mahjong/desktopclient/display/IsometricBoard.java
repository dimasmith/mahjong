package net.anatolich.mahjong.desktopclient.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Column;
import net.anatolich.mahjong.game.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Isometric view of mahjong board.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class IsometricBoard {

    private static final Logger logger = LoggerFactory.getLogger(IsometricBoard.class);
    private Board board;
    private int height, width;


    public IsometricBoard( int height, int width ) {
        this.height = height;
        this.width = width;
    }

    public IsometricBoard() {
    }

    public void draw( Graphics2D g ) {

        clearBoard(g);

        drawGrid(createGraphics(g));

        TileShape tileShape = new TileShape(2, 2, 0);
        tileShape.draw(createGraphics(g));

        TileShape tileShape2 = new TileShape(2, 2, 1);
        tileShape2.draw(createGraphics(g));

        TileShape tileShape3 = new TileShape(2, 2, 2);
        tileShape3.draw(createGraphics(g));

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

    private void drawGrid( Graphics2D g ) {
        g.setColor(Color.LIGHT_GRAY);
        BasicStroke stroke = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 1.0f, new float[]{ 20.0f, 1.0f }, 1.0f);
        g.setStroke(stroke);
        int step = 25;
        for ( int i = 0; i < width; i += step ) {
            g.drawLine(i, 0, i, height);
        }

        for ( int i = 0; i < height; i += step ) {
            g.drawLine(0, i, width, i);
        }
        g.dispose();
    }

    public Column getGameCoordinates(){
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Graphics2D createGraphics( Graphics2D g ) {
        return ( Graphics2D ) g.create();
    }

    public static class TileShape {

        private final CoordinateMapper coordinateMapper = new CoordinateMapper(10);

        private Point2D a, b, c, d, e, f;
        private final Coordinates baseCoordinates;

        private TileShape( int x, int y, int layer ) {
            this.baseCoordinates = new Coordinates(x, y, layer);
        }

        public void draw( Graphics2D g2 ) {
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1.0f));

            GeneralPath.Double footprintPath = new Path2D.Double();
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

            footprintPath.moveTo(a.getX(), a.getY());
            footprintPath.lineTo(b.getX(), b.getY());
            footprintPath.lineTo(c.getX(), c.getY());
            footprintPath.lineTo(d.getX(), d.getY());
            footprintPath.lineTo(e.getX(), e.getY());
            footprintPath.lineTo(f.getX(), f.getY());
            footprintPath.closePath();

            Point2D edgePoint = coordinateMapper.mapTo2D(fC.translate(0, 0, 1));

            g2.setColor(Color.WHITE);
            g2.fill(footprintPath);
            g2.setColor(Color.BLACK);
            g2.draw(footprintPath);

            Line2D edgeLine = new Line2D.Double(f, edgePoint);
            g2.draw(edgeLine);

            double tileWidth = coordinateMapper.getLengthAlongX(2);
            double tileHeight = coordinateMapper.getLengthAlongY(2);
            final Rectangle2D.Double topPane = new Rectangle2D.Double(b.getX(), b.getY(), tileWidth, tileHeight);
            g2.setColor(Color.LIGHT_GRAY);
            g2.fill(topPane);

            g2.setColor(Color.BLACK);
            g2.draw(topPane);

            g2.dispose();
        }

        public double getHeight(){
            return coordinateMapper.getLengthAlongY(2);
        }

        public double getWidth(){
            return coordinateMapper.getLengthAlongX(2);
        }
    }
}
