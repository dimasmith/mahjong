package net.anatolich.mahjong.game;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import static org.junit.Assert.*;
import static net.anatolich.mahjong.game.Tile.Type.*;
import static net.anatolich.mahjong.game.Tile.Value.*;

/**
 *
 * @author Dmytro
 */
public class AvailableMoveTest {

    /**
     * It is illegal to create {@link AvailableMove} instance for pieces with
     * equal coordinates.
     */
    @Test(expected = IllegalStateException.class)
    public void testCoordinatesInvariant() {
        final Piece p1 = new Piece(BAMBOOS, ONE, 0, 0, 0);
        final Piece p2 = new Piece(BAMBOOS, ONE, 0, 0, 0);

        AvailableMove availableMove = new AvailableMove(p1, p2);
        fail(String.format("Move %s must not be created", availableMove));
    }

    @Test
    public void testReversibleEquality() {
        final Piece p1 = new Piece(BAMBOOS, ONE, 0, 0, 0);
        final Piece p2 = new Piece(BAMBOOS, ONE, 2, 0, 0);

        AvailableMove availableMove = new AvailableMove(p1, p2);
        AvailableMove reverseMove = new AvailableMove(p2, p1);
        assertEquals(availableMove, reverseMove);
    }

    @Test
    public void testNormalEquality() {
        final Piece p1 = new Piece(BAMBOOS, ONE, 0, 0, 0);
        final Piece p2 = new Piece(BAMBOOS, ONE, 2, 0, 0);

        AvailableMove availableMove = new AvailableMove(p1, p2);
        AvailableMove reverseMove = new AvailableMove(p1, p2);
        assertEquals(availableMove, reverseMove);
    }

    @Test
    public void testEqualityOnRiskySetups() {
        final Piece p1 = new Piece(BAMBOOS, ONE, 0, 0, 0);
        final Piece p2 = new Piece(BAMBOOS, ONE, 2, 0, 0);
        final Piece p3 = new Piece(BAMBOOS, ONE, 4, 0, 0);

        AvailableMove availableMove = new AvailableMove(p1, p2);
        AvailableMove reverseMove = new AvailableMove(p2, p3);
        Assertions.assertThat(availableMove)
                .isNotEqualTo(reverseMove);

        availableMove = new AvailableMove(p2, p1);
        reverseMove = new AvailableMove(p2, p3);
        Assertions.assertThat(availableMove)
                .isNotEqualTo(reverseMove);
    }
    
    @Test
    public void testReversibleHashCode(){
        final Piece p1 = new Piece(BAMBOOS, ONE, 0, 0, 0);
        final Piece p2 = new Piece(BAMBOOS, ONE, 2, 0, 0);

        AvailableMove availableMove = new AvailableMove(p1, p2);
        AvailableMove reverseMove = new AvailableMove(p2, p1);
        assertEquals(availableMove.hashCode(), reverseMove.hashCode());
    }
}