package net.anatolich.mahjong.desktopclient.display;

import java.awt.geom.Point2D;
import net.anatolich.mahjong.game.Coordinates;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests conversion of 3D coordinates to 2D flat coordinates for rendering.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class CoordinateMapperTest {

    private final CoordinateMapper mapperPixelRatio10 = new CoordinateMapper(10);
    private final CoordinateMapper mapperPixelRatio20 = new CoordinateMapper(20);

    @Test
    public void test3DTo2D_110_pr10() {
        final Coordinates c = new Coordinates(1, 1, 0);
        final double x = 30.0;
        final double y = 40.0;
        assertPointEquals(c, mapperPixelRatio10.mapTo2D(c), x, y);
    }

    @Test
    public void test3DTo2D_111_pr10() {
        final Coordinates c = new Coordinates(1, 1, 1);
        final double x = 44.1;
        final double y = 25.8;
        assertPointEquals(c, mapperPixelRatio10.mapTo2D(c), x, y);
    }

    @Test
    public void test3DTo2D_222_pr10() {
        final Coordinates c = new Coordinates(2, 2, 2);
        final double x = 88.3;
        final double y = 51.7;
        assertPointEquals(c, mapperPixelRatio10.mapTo2D(c), x, y);
    }

    @Test
    public void test3DTo2D_421_pr10() {
        final Coordinates c = new Coordinates(4, 2, 1);
        final double x = 134.1;
        final double y = 65.8;
        assertPointEquals(c, mapperPixelRatio10.mapTo2D(c), x, y);
    }

    @Test
    public void test3DTo2D_111_pr20() {
        final Coordinates c = new Coordinates(1, 1, 1);
        final double x = 88.3;
        final double y = 51.7;
        assertPointEquals(c, mapperPixelRatio20.mapTo2D(c), x, y);
    }

    @Test
    public void test3DTo2D_421_pr20() {
        final Coordinates c = new Coordinates(4, 2, 1);
        final double x = 268.3;
        final double y = 131.7;
        assertPointEquals(c, mapperPixelRatio20.mapTo2D(c), x, y);
    }

    @Test
    public void testGetLengthAlongX(){
        final double expectedLength = 60;
        double length = mapperPixelRatio10.getLengthAlongX(2);
        assertEquals(expectedLength, length, 0.5);

    }

    @Test
    public void testGetLengthAlongY(){
        final double expectedLength = 80;
        double length = mapperPixelRatio10.getLengthAlongY(2);
        assertEquals(expectedLength, length, 0.5);

    }

    private void assertPointEquals( final Coordinates coordinates, Point2D actualPoint, final double expectedX,
                                    final double expectedY ) {
        assertEquals("Bad mapping for X coordinate " + coordinates, expectedX, actualPoint.getX(), 0.5);
        assertEquals("Bad mapping for Y coordinate " + coordinates, expectedY, actualPoint.getY(), 0.5);
    }
}
