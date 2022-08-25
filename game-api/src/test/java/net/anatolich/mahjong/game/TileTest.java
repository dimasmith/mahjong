package net.anatolich.mahjong.game;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class TileTest {

    /**
     * Checks that tile will throw error when it becomes constructed with invalid type/value combination
     */
    @Test
    public void testCreateInvalidTile() {
        // There are no tile of type wind and value summer
        final Tile.Type type = Tile.Type.WINDS;
        final Tile.Value value = Tile.Value.SUMMER;

        Assertions.assertThatExceptionOfType(InvalidTileException.class)
                .isThrownBy(() -> new Tile(type, value));
    }

    @Test
    public void testConstructorAssignment(){
        final Tile.Type type = Tile.Type.WINDS;
        final Tile.Value value = Tile.Value.EAST;

        final Tile tile = new Tile(type, value);

        Assertions.assertThat(tile.getType()).isEqualTo(type);
        Assertions.assertThat(tile.getValue()).isEqualTo(value);
    }
}
