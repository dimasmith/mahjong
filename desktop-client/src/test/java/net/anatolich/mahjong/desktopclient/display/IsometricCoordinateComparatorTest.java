package net.anatolich.mahjong.desktopclient.display;

import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.Tile;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class IsometricCoordinateComparatorTest {

    final Tile tile = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);
    final Coordinates base = new Coordinates(2, 2, 2);
    final IsometricCoordinateComparator comparator = new IsometricCoordinateComparator();

    private final Piece basePiece = new Piece(tile, base);
    private final Piece lowerPiece = new Piece(tile, new Coordinates(2, 2, 1));
    private final Piece leftPiece = new Piece(tile, new Coordinates(0, 2, 2));
    private final Piece topPiece = new Piece(tile, new Coordinates(2, 0, 2));

    @Test
    public void testCompare_SameCoordinates() {
        assertThat(comparator.compare(basePiece, basePiece), is(0));
    }

    @Test
    public void testCompare_LowerLevel() {
        assertBiggerThan(basePiece, lowerPiece);
    }

    @Test
    public void testCompare_UpperLevel() {
        assertLessThan(lowerPiece, basePiece);
    }

    @Test
    public void testCompare_Left(){
        assertBiggerThan(leftPiece, basePiece);
    }

    @Test
    public void testCompare_Right(){
        assertLessThan(basePiece, leftPiece);
    }

    @Test
    public void testCompare_Top(){
        assertLessThan(topPiece, basePiece);
    }

    @Test
    public void testCompare_Bottom(){
        assertBiggerThan(basePiece, topPiece);
    }

    private void assertLessThan(Piece first, Piece second){
        assertTrue(comparator.compare(first, second) < 0);
    }

    private void assertBiggerThan(Piece first, Piece second){
        assertTrue(comparator.compare(first, second) > 0);
    }
}
