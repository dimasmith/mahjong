package net.anatolich.mahjong.game;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class TileTest {

    /**
     * Checks that tile will throw error when it becomes constructed with invalid type/value combination
     */
    @Test(expected = InvalidTileException.class)
    public void testCreateInvalidTile() {
        // There are no tile of type wind and value summer
        final Tile.Type type = Tile.Type.WINDS;
        final Tile.Value value = Tile.Value.SUMMER;

        Tile tile = new Tile(type, value); // Constructor must throw an exception
    }

    @Test
    public void testConstructorAssignment(){
        final Tile.Type type = Tile.Type.WINDS;
        final Tile.Value value = Tile.Value.EAST;

        final Tile tile = new Tile(type, value);

        assertThat(tile.getType(), is(type));
        assertThat(tile.getValue(), is(value));
    }
}
