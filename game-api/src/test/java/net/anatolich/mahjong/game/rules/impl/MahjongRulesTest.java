package net.anatolich.mahjong.game.rules.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.Tile;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class MahjongRulesTest {

    private Board board;
    public final Tile bambooOne = new Tile(Tile.Type.BAMBOOS, Tile.Value.ONE);
    public final MahjongRules mahjongRules = new MahjongRules();

    @Before
    public void setUp() {
    }

    @Test
    public void testIsPieceOpen_MissingPiece() {
        Piece missingPiece = new Piece(bambooOne, new Coordinates(0, 0, 0));

        initializeBoard();

        assertThat("Missing piece cannot be open", mahjongRules.isPieceOpen(missingPiece.getCoordinates(), board), is(false));
    }

    @Test
    public void testIsPieceOpen_SinglePiece() {
        Piece singlePiece = new Piece(bambooOne, new Coordinates(0, 0, 0));

        initializeBoard(singlePiece);

        assertThat("Single piece must be open", mahjongRules.isPieceOpen(singlePiece.getCoordinates(), board), is(true));
    }

    @Test
    public void testIsPieceOpen_BlockedByUpperPiece() {
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
            initializeBoard(blockedPiece, blocker);
            assertThat(String.format("Piece at %s is blocked by piece at %s", blockedPiece.getCoordinates(), blocker.getCoordinates()),
                       mahjongRules.isPieceOpen(blockedPiece.getCoordinates(), board), is(false));
        }
    }

    @Test
    public void testIsPieceOpen_BothSidesBlocked() {
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
                initializeBoard(leftBlocker, blockedPiece, rightBlocker);
                assertThat(String.format("Piece at %s is side-blocked by pieces at %s and %s", blockedPiece.getCoordinates(), leftBlocker.getCoordinates(), rightBlocker.getCoordinates()),
                           mahjongRules.isPieceOpen(blockedPiece.getCoordinates(), board), is(false));


            }
        }
    }

    private void initializeBoard( Piece... pieces ) {
        board = EasyMock.createMock("board", Board.class);
        for ( Piece piece : pieces ) {
            EasyMock.expect(board.getPieceAt(piece.getCoordinates())).andReturn(piece);
        }

        EasyMock.expect(board.getPieceAt(EasyMock.anyObject(Coordinates.class))).andReturn(null).anyTimes();
        EasyMock.replay(board);
    }
}
