package net.anatolich.mahjong.mahjong;

import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.Tile;
import net.anatolich.mahjong.test.MockBoardBuilder;
import org.junit.Test;

import static net.anatolich.mahjong.game.Tile.Type.*;
import static net.anatolich.mahjong.game.Tile.Value.*;

import static org.junit.Assert.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class RulesMoveTest {

    /**
     * Checks that user cannot make move when selecting the same piece twice.
     */
    @Test
    public void ensureMoveWithSameTileIsIllegal(){
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Board board = MockBoardBuilder.createBoard(new Piece(new Tile(BAMBOOS, ONE), coordinates));

        MahjongRules rules = new MahjongRules();
        assertFalse("Move is illegal for the same tile picked up twice",
                    rules.isMoveLegal(coordinates, coordinates, board));
    }
}
