package net.anatolich.mahjong.desktopclient;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import net.anatolich.mahjong.game.BoardView;
import net.anatolich.mahjong.game.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Isometric view of mahjong board.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class IsometricBoard {

    private static final Logger logger = LoggerFactory.getLogger(IsometricBoard.class);
    private BoardView board;
    private int height, width;

    public IsometricBoard( int height, int width ) {
        this.height = height;
        this.width = width;
    }

    public IsometricBoard() {
    }

    public void draw( Graphics2D g ) {

        clearBoard(g);

        drawGrid(( Graphics2D ) g.create());

//        board.getAllTiles();

    }

    public BoardView getBoard() {
        return board;
    }

    public void setBoard( BoardView board ) {
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

    public static class TileShape {

        public static final double L_X_SHIFT = 12.5;
        public static final double L_Y_SHIFT = -12.5;
        public static final double WIDTH = 50;
        public static final double HEIGHT = 75;
        private final double gX, gY;
        private Point2D a, b, c, d, e, f;

        private TileShape( int x, int y, int layer ) {
            this.gX = x * WIDTH + ( layer * L_X_SHIFT );
            this.gY = y * HEIGHT + ( layer * L_Y_SHIFT );
        }

        public void draw( Graphics2D g2 ) {
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1.0f));

            GeneralPath.Double footprintPath = new Path2D.Double();
            a = new Point2D.Double(gX, gY);
            b = new Point2D.Double(gX + L_X_SHIFT, gY + L_Y_SHIFT);
            c = new Point2D.Double(gX + L_X_SHIFT + WIDTH, gY + L_Y_SHIFT);
            d = new Point2D.Double(gX + L_X_SHIFT + WIDTH, gY + L_Y_SHIFT + HEIGHT);
            e = new Point2D.Double(gX + WIDTH, gY + HEIGHT);
            f = new Point2D.Double(gX, gY + HEIGHT);

            footprintPath.moveTo(a.getX(), a.getY());
            footprintPath.lineTo(b.getX(), b.getY());
            footprintPath.lineTo(c.getX(), c.getY());
            footprintPath.lineTo(d.getX(), d.getY());
            footprintPath.lineTo(e.getX(), e.getY());
            footprintPath.lineTo(f.getX(), f.getY());
            footprintPath.closePath();

            Point2D point = new Point2D.Double(f.getX() + L_X_SHIFT, f.getY() + L_Y_SHIFT);

            g2.setColor(Color.WHITE);
            g2.fill(footprintPath);
            g2.setColor(Color.BLACK);
            g2.draw(footprintPath);

            Line2D line = new Line2D.Double(f, point);
            g2.draw(line);

            final Rectangle2D.Double topPane = new Rectangle2D.Double(b.getX(), b.getY(), WIDTH, HEIGHT);
            g2.setColor(Color.LIGHT_GRAY);
            g2.fill(topPane);

            g2.setColor(Color.BLACK);
            g2.draw(topPane);

            g2.dispose();
        }

        public double getHeight(){
            return HEIGHT;
        }

        public double getWidth(){
            return WIDTH;
        }
    }
}
