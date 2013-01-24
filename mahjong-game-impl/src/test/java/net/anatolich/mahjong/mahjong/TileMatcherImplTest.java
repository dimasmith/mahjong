package net.anatolich.mahjong.mahjong;

import net.anatolich.mahjong.game.Tile;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class TileMatcherImplTest {

    private TileMatcherImpl tileMatcher = new TileMatcherImpl();

    @Test
    public void testTypeOnlyMatch() {
        Tile seasonSpring = new Tile(Tile.Type.SEASONS, Tile.Value.SPRING);
        Tile seasonSummer = new Tile(Tile.Type.SEASONS, Tile.Value.SUMMER);
        Tile flowerPlum = new Tile(Tile.Type.FLOWERS, Tile.Value.PLUM);
        Tile flowerOrchid = new Tile(Tile.Type.FLOWERS, Tile.Value.ORCHID);

        assertTileMatch(seasonSpring, seasonSpring);
        assertTileMatch(seasonSpring, seasonSummer);

        assertTileMatch(flowerPlum, flowerPlum);
        assertTileMatch(flowerPlum, flowerOrchid);

        assertTileNotMatch(seasonSpring, flowerPlum);
    }

    @Test
    public void testTypeValueMatch() {
        Tile bambooOne = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);
        Tile bambooTwo = new Tile(Tile.Type.BAMBOOS, Tile.Value.TWO);
        Tile dragonRed = new Tile(Tile.Type.DRAGONS, Tile.Value.RED);
        Tile dragonWhite = new Tile(Tile.Type.DRAGONS, Tile.Value.WHITE);

        assertTileMatch(bambooOne, bambooOne);
        assertTileNotMatch(bambooOne, bambooTwo);

        assertTileMatch(dragonRed, dragonRed);
        assertTileNotMatch(dragonRed, dragonWhite);

        assertTileNotMatch(bambooOne, dragonRed);
    }

    private void assertTileMatch( Tile t1, Tile t2 ) {
        assertTrue(tileMatcher.match(t1, t2));
    }

    private void assertTileNotMatch( Tile t1, Tile t2 ) {
        assertFalse(tileMatcher.match(t1, t2));
    }
}
