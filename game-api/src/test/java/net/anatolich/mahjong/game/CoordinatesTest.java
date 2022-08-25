package net.anatolich.mahjong.game;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class CoordinatesTest {

    @Test
    public void testConstructorSetUp() {
        final int x = 10;
        final int y = 20;
        final int l = 30;

        Coordinates coordinates = new Coordinates(x, y, l);

        Assertions.assertThat(coordinates.getX()).isEqualTo(x);
        Assertions.assertThat(coordinates.getY()).isEqualTo(y);
        Assertions.assertThat(coordinates.getLayer()).isEqualTo(l);
    }

    @Test
    public void testConstructorSetUp_Column() {
        final int x = 10;
        final int y = 20;
        final int l = 30;

        Coordinates coordinates = new Coordinates(new Column(x, y), l);

        Assertions.assertThat(coordinates.getX()).isEqualTo(x);
        Assertions.assertThat(coordinates.getY()).isEqualTo(y);
        Assertions.assertThat(coordinates.getLayer()).isEqualTo(l);
    }

    @Test
    public void testIsOnColumn() {
        Coordinates coordinates = new Coordinates(0, 0, 0);
        Column column = new Column(0, 0);

        Assertions.assertThat(coordinates.isOnColumn(column)).isTrue();
    }

    @Test
    public void testIsOnColumn_Failure() {
        final Column column = new Column(0, 0);
        final Coordinates coordinates1 = new Coordinates(1, 0, 0);
        final Coordinates coordinates2 = new Coordinates(0, 1, 0);

        Assertions.assertThat(coordinates1.isOnColumn(column)).isFalse();
        Assertions.assertThat(coordinates2.isOnColumn(column)).isFalse();
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

        Assertions.assertThat(coords1).isEqualTo(coords2)
                .isNotEqualTo(coords3)
                .isNotEqualTo(coords4)
                .isNotEqualTo(coords5);
    }

    @Test
    public void testGetColumn() {
        final int x = 10;
        final int y = 20;
        final int layer = 30;

        final Coordinates coordinates = new Coordinates(x, y, layer);
        final Column expectedColumn = new Column(x, y);

        Assertions.assertThat(coordinates.getColumn()).isEqualTo(expectedColumn);
    }

    @Test
    public void testTranslate_2_0_0() {
        Coordinates coords = new Coordinates(1, 2, 3);
        Coordinates translated = coords.translate(2, 0, -1);

        Assertions.assertThat(coords.getX()).isEqualTo(1);
        Assertions.assertThat(translated).isEqualTo(new Coordinates(3, 2, 2));
        Assertions.assertThat(coords)
                .as("original coordinates must not change")
                .isEqualTo(new Coordinates(1, 2, 3));

        assertNotSame(coords, translated);
    }

    @Test
    public void testTranslateX_i2_0_0() {
        Coordinates coords = new Coordinates(3, 2, 3);
        Coordinates translated = coords.translate(-2, 2, 1);

        Assertions.assertThat(translated).isEqualTo(new Coordinates(1, 4, 4));
        Assertions.assertThat(coords)
                .as("Original coordinates must not change")
                .isEqualTo(new Coordinates(3, 2, 3));
        assertNotSame(coords, translated);
    }


    @Test
    public void testTranslateCoords_2_0_0() {
        Coordinates coords = new Coordinates(1, 2, 3);
        final Coordinates translation = new Coordinates(2, 0, -1);
        Coordinates translated = coords.translate(translation);

        Assertions.assertThat(coords.getX()).isEqualTo(1);
        Assertions.assertThat(translated).isEqualTo(new Coordinates(3, 2, 2));
        Assertions.assertThat(coords).as("Original coordinates must not change")
                .isEqualTo(new Coordinates(1, 2, 3));
        assertNotSame(coords, translated);
    }

    @Test
    public void testTranslateCoords_i2_0_0() {
        Coordinates coords = new Coordinates(3, 2, 3);
        final Coordinates translation = new Coordinates(-2, 2, 1);
        Coordinates translated = coords.translate(translation);

        Assertions.assertThat(translated).isEqualTo(new Coordinates(1, 4, 4));
        Assertions.assertThat(coords)
                .as("Original coordinates must not change")
                .isEqualTo(new Coordinates(3, 2, 3));
        assertNotSame(coords, translated);
    }
}
