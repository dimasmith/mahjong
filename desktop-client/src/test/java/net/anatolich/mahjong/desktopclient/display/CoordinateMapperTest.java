package net.anatolich.mahjong.desktopclient.display;

import java.awt.geom.Point2D;
import net.anatolich.mahjong.game.Coordinates;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class CoordinateMapperTest {

    @Test
    public void test3DTo2D_110() {
        final Coordinates c = new Coordinates(1, 1, 0);
        final int pixelRatio = 10;

        final CoordinateMapper mapper = new CoordinateMapper(pixelRatio);

        Point2D point = mapper.mapTo2D(c);

        assertEquals("Bad mapping for X coordinate " + c, 30.0, point.getX(), 0.5);
        assertEquals("Bad mapping for Y coordinate " + c, 40.0, point.getY(), 0.5);
    }

    @Test
    public void test3DTo2D_111() {
        final Coordinates c = new Coordinates(1, 1, 1);
        final int pixelRatio = 10;

        final CoordinateMapper mapper = new CoordinateMapper(pixelRatio);

        Point2D point = mapper.mapTo2D(c);

        assertEquals("Bad mapping for X coordinate " + c, 44.1, point.getX(), 0.5);
        assertEquals("Bad mapping for Y coordinate " + c, 25.8, point.getY(), 0.5);
    }

    @Test
    public void test3DTo2D_222() {
        final Coordinates c = new Coordinates(2, 2, 2);
        final int pixelRatio = 10;

        final CoordinateMapper mapper = new CoordinateMapper(pixelRatio);

        Point2D point = mapper.mapTo2D(c);

        assertEquals("Bad mapping for X coordinate " + c, 88.3, point.getX(), 0.5);
        assertEquals("Bad mapping for Y coordinate " + c, 51.7, point.getY(), 0.5);
    }

    @Test
    public void test3DTo2D_421() {
        final Coordinates c = new Coordinates(4, 2, 1);
        final int pixelRatio = 10;

        final CoordinateMapper mapper = new CoordinateMapper(pixelRatio);

        Point2D point = mapper.mapTo2D(c);

        assertEquals("Bad mapping for X coordinate " + c, 134.1, point.getX(), 0.5);
        assertEquals("Bad mapping for Y coordinate " + c, 65.8, point.getY(), 0.5);
    }
}
