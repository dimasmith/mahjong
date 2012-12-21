package net.anatolich.mahjong.game;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class TileColumnResolverTest {

    @Test
    public void testResolveLayout() {
        Map<Coordinates, Tile> layout = new HashMap<>();
        final Tile tileWest =  new Tile(Tile.Type.WINDS, Tile.Value.WEST);
        final Tile tileEast =  new Tile(Tile.Type.WINDS, Tile.Value.EAST);
        final Tile tileNorth = new Tile(Tile.Type.WINDS, Tile.Value.NORTH);
        final Tile tileSouth = new Tile(Tile.Type.WINDS, Tile.Value.SOUTH);

        final Coordinates west = new Coordinates(2, 2, 0);
        final Coordinates east = new Coordinates(2, 3, 1);
        final Coordinates north = new Coordinates(4, 4, 0);
        final Coordinates south = new Coordinates(4, 4, 1);

        layout.put(west, tileWest);
        layout.put(east, tileEast);
        layout.put(north, tileNorth);
        layout.put(south, tileSouth);


        final Board board = new Board();
        TileColumnResolver resolver = new TileColumnResolver();
        resolver.layTilesOnBoard(layout, board);

        assertThat(board.getTileAt(north), is(tileNorth));
        assertThat(board.getTileAt(south), is(tileSouth));
        assertThat(board.getTileAt(east), is(tileEast));
        assertThat(board.getTileAt(west), is(tileWest));

    }
}
