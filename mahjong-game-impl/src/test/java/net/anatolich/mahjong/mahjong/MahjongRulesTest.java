package net.anatolich.mahjong.mahjong;

import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.Tile;
import net.anatolich.mahjong.test.MockBoardBuilder;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static net.anatolich.mahjong.test.PieceBuilder.makePiece;

import static org.junit.Assert.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class MahjongRulesTest {

    private BlockChecker blockChecker;
    private TileMatcher tileMatcher;
    private MahjongRules mahjongRules;

    @Before
    public void setUp(){
        blockChecker = EasyMock.createMock("blockChecker", BlockChecker.class);
        tileMatcher = EasyMock.createMock("tileMatcher", TileMatcher.class);

        mahjongRules = new MahjongRules(blockChecker, tileMatcher);
    }

    /**
     * Checks that user cannot make move when selecting the same piece twice.
     */
    @Test
    public void ensureMoveWithSameTileIsIllegal() {
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Piece piece = makePiece().bamboo().one().at(coordinates);
        final Board board = MockBoardBuilder.createBoard(piece);

        assertFalse("Move is illegal for the same tile picked up twice",
                    mahjongRules.isMoveLegal(coordinates, coordinates, board));
    }

    @Test
    public void testIsOpen(){
        final Coordinates coordinates = new Coordinates(0, 0, 0);
        final Board board = MockBoardBuilder.createBoard();

        EasyMock.expect(blockChecker.isBlocked(coordinates, board)).andReturn(true);
        EasyMock.replay(blockChecker);

        assertFalse(mahjongRules.isPieceOpen(coordinates, board));

        EasyMock.reset(blockChecker);
        EasyMock.expect(blockChecker.isBlocked(coordinates, board)).andReturn(false);
        EasyMock.replay(blockChecker);

        assertTrue(mahjongRules.isPieceOpen(coordinates, board));
    }

    @Test
    public void testMoveWithFirstPieceBlocked(){
        final Coordinates c1 = new Coordinates(0, 0, 0);
        final Coordinates c2 = c1.translate(2, 0, 0);
        final Board board = MockBoardBuilder.createBoard();

        EasyMock.expect(blockChecker.isBlocked(c1, board)).andReturn(true);
        EasyMock.expect(blockChecker.isBlocked(c2, board)).andReturn(false);
        EasyMock.replay(blockChecker);

        assertFalse(mahjongRules.isMoveLegal(c1, c2, board));
    }

    @Test
    public void testMoveWithSecondPieceBlocked(){
        final Coordinates c1 = new Coordinates(0, 0, 0);
        final Coordinates c2 = c1.translate(2, 0, 0);
        final Board board = MockBoardBuilder.createBoard();

        EasyMock.expect(blockChecker.isBlocked(c1, board)).andReturn(false);
        EasyMock.expect(blockChecker.isBlocked(c2, board)).andReturn(true);
        EasyMock.replay(blockChecker);

        assertFalse(mahjongRules.isMoveLegal(c1, c2, board));
    }

    @Test
    public void testMoveWithNotMatchingTiles(){
        final Coordinates c1 = new Coordinates(0, 0, 0);
        final Coordinates c2 = c1.translate(2, 0, 0);
        final Board board = MockBoardBuilder.createBoard(
                makePiece().bamboo().one().at(c1),
                makePiece().bamboo().two().at(c2)
                );

        EasyMock.expect(blockChecker.isBlocked(c1, board)).andReturn(false);
        EasyMock.expect(blockChecker.isBlocked(c2, board)).andReturn(false);
        EasyMock.replay(blockChecker);

        EasyMock.expect(tileMatcher.match(EasyMock.anyObject(Tile.class), EasyMock.anyObject(Tile.class))).andReturn(false);
        EasyMock.replay(tileMatcher);

        assertFalse(mahjongRules.isMoveLegal(c1, c2, board));
    }

    @Test
    public void testMoveWithMatchingTiles(){
        final Coordinates c1 = new Coordinates(0, 0, 0);
        final Coordinates c2 = c1.translate(2, 0, 0);
        final Board board = MockBoardBuilder.createBoard(
                makePiece().bamboo().one().at(c1),
                makePiece().bamboo().two().at(c2)
                );

        EasyMock.expect(blockChecker.isBlocked(c1, board)).andReturn(false);
        EasyMock.expect(blockChecker.isBlocked(c2, board)).andReturn(false);
        EasyMock.replay(blockChecker);

        EasyMock.expect(tileMatcher.match(EasyMock.anyObject(Tile.class), EasyMock.anyObject(Tile.class))).andReturn(true);
        EasyMock.replay(tileMatcher);

        assertTrue(mahjongRules.isMoveLegal(c1, c2, board));
    }
}
