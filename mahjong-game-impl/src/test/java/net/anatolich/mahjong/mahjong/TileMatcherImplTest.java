package net.anatolich.mahjong.mahjong;

import net.anatolich.mahjong.game.Tile;
import org.junit.Test;

import static net.anatolich.mahjong.game.Tile.Type.*;
import static net.anatolich.mahjong.game.Tile.Value.*;

import static org.junit.Assert.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class TileMatcherImplTest {

    private TileMatcher tileMatcher = new TileMatcherImpl();

    @Test
    public void testTypeOnlyMatch() {
        Tile seasonSpring = new Tile(SEASONS, SPRING);
        Tile seasonSummer = new Tile(SEASONS, SUMMER);
        Tile flowerPlum = new Tile(FLOWERS, PLUM);
        Tile flowerOrchid = new Tile(FLOWERS, ORCHID);

        assertTileMatch(seasonSpring, seasonSpring);
        assertTileMatch(seasonSpring, seasonSummer);

        assertTileMatch(flowerPlum, flowerPlum);
        assertTileMatch(flowerPlum, flowerOrchid);

        assertTileNotMatch(seasonSpring, flowerPlum);
    }

    @Test
    public void testTypeValueMatch() {
        Tile bambooOne = new Tile(BAMBOOS, ONE);
        Tile bambooTwo = new Tile(BAMBOOS, TWO);
        Tile dragonRed = new Tile(DRAGONS, RED);
        Tile dragonWhite = new Tile(DRAGONS, WHITE);

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
