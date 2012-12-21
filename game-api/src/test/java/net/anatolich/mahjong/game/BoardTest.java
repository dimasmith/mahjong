package net.anatolich.mahjong.game;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class BoardTest {

    @Test
    public void testPutTile_SimplePlacement() {
        final Column column = new Column(0, 0);
        final Tile tile = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);

        Board board = new Board();

        board.putTile(tile, column);

        final Coordinates expectedCoordinates = new Coordinates(0, 0, 0);
        assertThat(board.getTileAt(expectedCoordinates), is(tile));
    }

    @Test
    public void testPutTitle_SimpleOnTopPlacement() {
        final Column column = new Column(0, 0);

        final Tile tile1 = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);
        final Tile tile2 = new Tile(Tile.Type.BAMBOOS, Tile.Value.TWO);

        Board board = new Board();

        board.putTile(tile1, column);
        board.putTile(tile2, column);

        final Coordinates expectedCoordinates1 = new Coordinates(0, 0, 0);
        final Coordinates expectedCoordinates2 = new Coordinates(0, 0, 1);

        assertThat(board.getTileAt(expectedCoordinates1), is(tile1));
        assertThat(board.getTileAt(expectedCoordinates2), is(tile2));
    }

    @Test
    public void testPutTitle_ShiftedOnTopPlacement() {
        final Column baseColumn = new Column(2, 2);

        final Tile baseTile = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);

        final Tile tileNW = new Tile(Tile.Type.BAMBOOS, Tile.Value.TWO);
        final Tile tileNE = new Tile(Tile.Type.BAMBOOS, Tile.Value.THREE);
        final Tile tileSW = new Tile(Tile.Type.BAMBOOS, Tile.Value.FOUR);
        final Tile tileSE = new Tile(Tile.Type.BAMBOOS, Tile.Value.FIVE);

        final Column columnNW = new Column(1, 1);
        final Column columnNE = new Column(3, 1);
        final Column columnSW = new Column(1, 3);
        final Column columnSE = new Column(3, 3);

        final Coordinates expectedCoordinatesNW = new Coordinates(columnNW, 1);
        final Coordinates expectedCoordinatesNE = new Coordinates(columnNE, 1);
        final Coordinates expectedCoordinatesSW = new Coordinates(columnSW, 1);
        final Coordinates expectedCoordinatesSE = new Coordinates(columnSE, 1);

        assertTileIsOnTopOfBase(baseColumn, baseTile, columnNW, tileNW, expectedCoordinatesNW);
        assertTileIsOnTopOfBase(baseColumn, baseTile, columnNE, tileNE, expectedCoordinatesNE);
        assertTileIsOnTopOfBase(baseColumn, baseTile, columnSW, tileSW, expectedCoordinatesSW);
        assertTileIsOnTopOfBase(baseColumn, baseTile, columnSE, tileSE, expectedCoordinatesSE);
    }

    private void assertTileIsOnTopOfBase( final Column baseColumn, final Tile baseTile, Column newColumn,
                                          final Tile newTile, final Coordinates expectedCoordinates ) {
        Board board = new Board();

        board.putTile(baseTile, baseColumn);
        board.putTile(newTile, newColumn);

        assertThat(board.getTileAt(expectedCoordinates), is(newTile));
    }

    @Test
    public void testGetTopmostTile_EmptyColumn(){
        final Board board = new Board();
        final Column column = new Column(0, 0);
        assertThat(board.getTopmostTile(column), nullValue());
    }

    @Test
    public void testGetTopmostTile_SingleTile(){
        final Tile baseLevelTile = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);
        final Column column = new Column(0, 0);

        final Board board = new Board();
        board.putTile(baseLevelTile, column);

        assertThat(board.getTopmostTile(column), is(baseLevelTile));
    }

    @Test
    public void testGetTopmostColumnTile(){
        final Tile baseLevelTile = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);
        final Tile secondLevelTile = new Tile(Tile.Type.BAMBOOS, Tile.Value.TWO);

        final Column column = new Column(0, 0);

        final Board board = new Board();
        board.putTile(baseLevelTile, column);
        board.putTile(secondLevelTile, column);

        assertThat(board.getTopmostTile(column), is(secondLevelTile));


    }
}
