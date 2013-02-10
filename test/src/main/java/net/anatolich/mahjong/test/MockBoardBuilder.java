package net.anatolich.mahjong.test;

import java.util.Arrays;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;
import org.easymock.EasyMock;

/**
 * Mock implementation of game board.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 * @since 1.0
 * @version 1.0
 */
public class MockBoardBuilder {

    public static Board createBoard( Piece... pieces ) {
        Board board = EasyMock.createMock("board", Board.class);
        for ( Piece piece : pieces ) {
            EasyMock.expect(board.getPieceAt(piece.getCoordinates())).andReturn(piece).anyTimes();
        }

        EasyMock.expect(board.getPieceAt(EasyMock.anyObject(Coordinates.class))).andReturn(null).anyTimes();
        
        EasyMock.expect(board.getAllPieces()).andReturn(Arrays.asList(pieces)).anyTimes();
        EasyMock.replay(board);
        return board;
    }
}
