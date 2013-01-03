package net.anatolich.mahjong.desktopclient.display;

import java.awt.geom.Point2D;
import net.anatolich.mahjong.game.Coordinates;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests conversion of 3D coordinates to 2D flat coordinates for rendering.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class CoordinateMapperTest {

    @Test
    public void test3DTo2D_110() {
        final Coordinates c = new Coordinates(1, 1, 0);
        final int pixelRatio = 10;

        final CoordinateMapper mapper = new CoordinateMapper(pixelRatio);

        Point2D actualPoint = mapper.mapTo2D(c);
        final double x = 30.0;
        final double y = 40.0;
        assertPointEquals(c, actualPoint, x, y);
    }

    @Test
    public void test3DTo2D_111() {
        final Coordinates c = new Coordinates(1, 1, 1);
        final int pixelRatio = 10;

        final CoordinateMapper mapper = new CoordinateMapper(pixelRatio);

        Point2D actualPoint = mapper.mapTo2D(c);
        final double x = 44.1;
        final double y = 25.8;
        assertPointEquals(c, actualPoint, x, y);
    }

    @Test
    public void test3DTo2D_222() {
        final Coordinates c = new Coordinates(2, 2, 2);
        final int pixelRatio = 10;

        final CoordinateMapper mapper = new CoordinateMapper(pixelRatio);

        Point2D actualPoint = mapper.mapTo2D(c);
        final double x = 88.3;
        final double y = 51.7;
        assertPointEquals(c, actualPoint, x, y);
    }

    @Test
    public void test3DTo2D_421() {
        final Coordinates c = new Coordinates(4, 2, 1);
        final int pixelRatio = 10;

        final CoordinateMapper mapper = new CoordinateMapper(pixelRatio);

        Point2D actualPoint = mapper.mapTo2D(c);
        final double x = 134.1;
        final double y = 65.8;
        assertPointEquals(c, actualPoint, x, y);
    }

    private void assertPointEquals( final Coordinates coordinates, Point2D actualPoint, final double expectedX,
                                    final double expectedY ) {
        assertEquals("Bad mapping for X coordinate " + coordinates, expectedX, actualPoint.getX(), 0.5);
        assertEquals("Bad mapping for Y coordinate " + coordinates, expectedY, actualPoint.getY(), 0.5);
    }
}
