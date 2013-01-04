package net.anatolich.mahjong.desktopclient.display;

import net.anatolich.mahjong.game.Coordinates;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class TilePainterTest {


    @Test
    public void testBoundingBoxCoordinates() {
        final Coordinates leftTopLower = new Coordinates(1, 2, 3);
        final Coordinates leftTopUpper = new Coordinates(1, 2, 4);
        final Coordinates rightTopLower = new Coordinates(3, 2, 3);
        final Coordinates rightTopUpper = new Coordinates(3, 2, 4);
        final Coordinates leftBottomLower = new Coordinates(1, 4, 3);
        final Coordinates leftBottomUpper = new Coordinates(1, 4, 4);
        final Coordinates rightBottomLower = new Coordinates(3, 4, 3);
        final Coordinates rightBottomUpper = new Coordinates(3, 4, 4);

        TilePainter tilePainter = new TilePainter(leftTopLower);

        assertThat("Incorrect left top lower point", tilePainter.getBoundingPoint(TilePainter.BoundingPoints.LEFT_TOP_LOWER), is(leftTopLower));
        assertThat("Incorrect left top upper point", tilePainter.getBoundingPoint(TilePainter.BoundingPoints.LEFT_TOP_UPPER), is(leftTopUpper));
        assertThat("Incorrect right top lower point", tilePainter.getBoundingPoint(TilePainter.BoundingPoints.RIGHT_TOP_LOWER), is(rightTopLower));
        assertThat("Incorrect right top upper point", tilePainter.getBoundingPoint(TilePainter.BoundingPoints.RIGHT_TOP_UPPER), is(rightTopUpper));
        assertThat("Incorrect left bottom lower point", tilePainter.getBoundingPoint(TilePainter.BoundingPoints.LEFT_BOTTOM_LOWER), is(leftBottomLower));
        assertThat("Incorrect left bottom upper point", tilePainter.getBoundingPoint(TilePainter.BoundingPoints.LEFT_BOTTOM_UPPER), is(leftBottomUpper));
        assertThat("Incorrect right bottom lower point", tilePainter.getBoundingPoint(TilePainter.BoundingPoints.RIGHT_BOTTOM_LOWER), is(rightBottomLower));
        assertThat("Incorrect right bottom upper point", tilePainter.getBoundingPoint(TilePainter.BoundingPoints.RIGHT_BOTTOM_UPPER), is(rightBottomUpper));

    }
}
