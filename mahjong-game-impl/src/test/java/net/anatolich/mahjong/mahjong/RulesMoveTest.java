package net.anatolich.mahjong.mahjong;

import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.Tile;
import org.easymock.EasyMock;
import org.junit.Test;
import static org.junit.Assert.*;
import static net.anatolich.mahjong.game.Tile.Type.*;
import static net.anatolich.mahjong.game.Tile.Value.*;

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
        final Board board = initializeBoard(new Piece(new Tile(BAMBOOS, ONE), coordinates));

        MahjongRules rules = new MahjongRules();
        assertFalse("Move is illegal for the same tile picked up twice",
                    rules.isMoveLegal(coordinates, coordinates, board));
    }

    private Board initializeBoard( Piece... pieces ) {
        Board board = EasyMock.createMock("board", Board.class);
        for ( Piece piece : pieces ) {
            EasyMock.expect(board.getPieceAt(piece.getCoordinates())).andReturn(piece).anyTimes();
        }

        EasyMock.expect(board.getPieceAt(EasyMock.anyObject(Coordinates.class))).andReturn(null).anyTimes();
        EasyMock.replay(board);
        return board;
    }
}
