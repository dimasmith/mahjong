package net.anatolich.mahjong.mahjong;

import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.Tile;
import net.anatolich.mahjong.test.MockBoardBuilder;
import org.junit.Test;

import static net.anatolich.mahjong.test.PieceBuilder.makePiece;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class BlockedPieceCheckerTest {

    public final Tile bambooOne = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);
    public final BlockedPieceChecker mahjongRules = new BlockedPieceChecker();

    @Test
    public void testIsPieceBlocked_MissingPiece() {
        Piece missingPiece = makePiece().bamboo().one().at(0, 0, 0);

        Board board = MockBoardBuilder.createBoard();

        assertThat("Missing piece is blocked", mahjongRules.isBlocked(missingPiece.getCoordinates(), board), is(true));
    }

    @Test
    public void testIsPieceBlocked_SinglePiece() {
        Piece singlePiece = makePiece().bamboo().one().at(0, 0, 0);

        Board board = MockBoardBuilder.createBoard(singlePiece);

        assertThat("Single piece is always open", mahjongRules.isBlocked(singlePiece.getCoordinates(), board), is(false));
    }

    @Test
    public void testPieceBlockedByUpperPiece() {
        final Coordinates base = new Coordinates(2, 2, 0);
        final Piece blockedPiece = new Piece(bambooOne, base);

        final Piece upperLeftBackPiece = new Piece(bambooOne, base.translate(-1, -1, 1));
        final Piece upperLeftPiece = new Piece(bambooOne, base.translate(-1, 0, 1));
        final Piece upperLeftFrontPiece = new Piece(bambooOne, base.translate(-1, 1, 1));

        final Piece upperBackPiece = new Piece(bambooOne, base.translate(0, -1, 1));
        final Piece upperPiece = new Piece(bambooOne, base.translate(0, 0, 1));
        final Piece upperFrontPiece = new Piece(bambooOne, base.translate(0, 1, 1));

        final Piece upperRightBackPiece = new Piece(bambooOne, base.translate(1, -1, 1));
        final Piece upperRightPiece = new Piece(bambooOne, base.translate(1, 0, 1));
        final Piece upperRightFrontPiece = new Piece(bambooOne, base.translate(1, 1, 1));

        final Piece[] blockers = new Piece[]{
            upperLeftBackPiece,
            upperLeftPiece,
            upperLeftFrontPiece,
            upperBackPiece,
            upperPiece,
            upperFrontPiece,
            upperRightBackPiece,
            upperRightPiece,
            upperRightFrontPiece
        };

        for ( Piece blocker : blockers ) {
            Board board = MockBoardBuilder.createBoard(blockedPiece, blocker);
            assertThat(String.format("Piece at %s is blocked by piece at %s", blockedPiece.getCoordinates(), blocker.getCoordinates()),
                       mahjongRules.isBlocked(blockedPiece.getCoordinates(), board), is(true));
        }
    }

    @Test
    public void testPieceBlockedFromBothSides() {
        final Coordinates base = new Coordinates(2, 2, 0);
        Piece blockedPiece = new Piece(bambooOne, base);

        final Piece[] leftBlockers = new Piece[]{
            new Piece(bambooOne, base.translate(-2, -1, 0)),
            new Piece(bambooOne, base.translate(-2, 0, 0)),
            new Piece(bambooOne, base.translate(-2, 1, 0))
        };

        final Piece[] rightBlockers = new Piece[]{
            new Piece(bambooOne, base.translate(2, -1, 0)),
            new Piece(bambooOne, base.translate(2, 0, 0)),
            new Piece(bambooOne, base.translate(2, 1, 0))
        };

        for ( Piece leftBlocker : leftBlockers ) {
            for ( Piece rightBlocker : rightBlockers ) {
                Board board = MockBoardBuilder.createBoard(leftBlocker, blockedPiece, rightBlocker);
                assertThat(String.format("Piece at %s is side-blocked by pieces at %s and %s", blockedPiece.getCoordinates(), leftBlocker.getCoordinates(), rightBlocker.getCoordinates()),
                           mahjongRules.isBlocked(blockedPiece.getCoordinates(), board), is(true));


            }
        }
    }
}
