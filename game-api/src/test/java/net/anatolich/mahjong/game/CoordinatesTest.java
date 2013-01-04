package net.anatolich.mahjong.game;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class CoordinatesTest {

    @Test
    public void testConstructorSetUp() {
        final int x = 10;
        final int y = 20;
        final int l = 30;

        Coordinates coordinates = new Coordinates(x, y, l);

        assertThat(coordinates.getX(), is(x));
        assertThat(coordinates.getY(), is(y));
        assertThat(coordinates.getLayer(), is(l));
    }

    @Test
    public void testConstructorSetUp_Column() {
        final int x = 10;
        final int y = 20;
        final int l = 30;

        Coordinates coordinates = new Coordinates(new Column(x, y), l);

        assertThat(coordinates.getX(), is(x));
        assertThat(coordinates.getY(), is(y));
        assertThat(coordinates.getLayer(), is(l));
    }

    @Test
    public void testIsOnColumn() {
        Coordinates coordinates = new Coordinates(0, 0, 0);
        Column column = new Column(0, 0);

        assertThat(coordinates.isOnColumn(column), is(true));
    }

    @Test
    public void testIsOnColumn_Failure() {
        final Column column = new Column(0, 0);
        final Coordinates coordinates1 = new Coordinates(1, 0, 0);
        final Coordinates coordinates2 = new Coordinates(0, 1, 0);

        assertThat(coordinates1.isOnColumn(column), is(false));
        assertThat(coordinates2.isOnColumn(column), is(false));
    }

    @Test
    public void testEquals_Success() {
        final int x = 10;
        final int y = 20;
        final int l = 30;
        final int otherValue = 5;

        final Coordinates coords1 = new Coordinates(x, y, l);
        final Coordinates coords2 = new Coordinates(x, y, l);

        final Coordinates coords3 = new Coordinates(otherValue, y, l);
        final Coordinates coords4 = new Coordinates(x, otherValue, l);
        final Coordinates coords5 = new Coordinates(x, y, otherValue);

        assertThat(coords1, is(coords2));

        assertThat(coords1, not(is(coords3)));
        assertThat(coords1, not(is(coords4)));
        assertThat(coords1, not(is(coords5)));
    }

    @Test
    public void testGetColumn() {
        final int x = 10;
        final int y = 20;
        final int layer = 30;

        final Coordinates coordinates = new Coordinates(x, y, layer);
        final Column expectedColumn = new Column(x, y);

        assertThat(coordinates.getColumn(), is(expectedColumn));
    }

    @Test
    public void testTranslate_2_0_0() {
        Coordinates coords = new Coordinates(1, 2, 3);
        Coordinates translated = coords.translate(2, 0, -1);

        assertThat(coords.getX(), is(1));
        assertThat(translated, is(new Coordinates(3, 2, 2)));
        assertThat("Original coordinates must not change", coords, is(new Coordinates(1, 2, 3)));
        assertNotSame(coords, translated);
    }

    @Test
    public void testTranslateX_i2_0_0() {
        Coordinates coords = new Coordinates(3, 2, 3);
        Coordinates translated = coords.translate(-2, 2, 1);

        assertThat(translated, is(new Coordinates(1, 4, 4)));
        assertThat("Original coordinates must not change", coords, is(new Coordinates(3, 2, 3)));
        assertNotSame(coords, translated);
    }


    @Test
    public void testTranslateCoords_2_0_0() {
        Coordinates coords = new Coordinates(1, 2, 3);
        final Coordinates translation = new Coordinates(2, 0, -1);
        Coordinates translated = coords.translate(translation);

        assertThat(coords.getX(), is(1));
        assertThat(translated, is(new Coordinates(3, 2, 2)));
        assertThat("Original coordinates must not change", coords, is(new Coordinates(1, 2, 3)));
        assertNotSame(coords, translated);
    }

    @Test
    public void testTranslateCoords_i2_0_0() {
        Coordinates coords = new Coordinates(3, 2, 3);
        final Coordinates translation = new Coordinates(-2, 2, 1);
        Coordinates translated = coords.translate(translation);

        assertThat(translated, is(new Coordinates(1, 4, 4)));
        assertThat("Original coordinates must not change", coords, is(new Coordinates(3, 2, 3)));
        assertNotSame(coords, translated);
    }
}
