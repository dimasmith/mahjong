package net.anatolich.mahjong.mahjong;

import net.anatolich.mahjong.game.Piece;
import static net.anatolich.mahjong.test.GameSessionMatcher.*;
import static net.anatolich.mahjong.test.PieceBuilder.makePiece;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import org.junit.Assert;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests session implementation for correct behavior.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 * @version 1.0
 * @since 1.0
 */
public class GameSessionImplTest {
    private GameStateBuilder gameStateBuilder;

    @Before
    public void createGameStateBuilder() {
        gameStateBuilder = new GameStateBuilder();
    }

    /**
     * Marked tiles list is empty. Picking up blocked tile. No changes should be
     * in marked tiles list. No move should be occurred.
     */
    @Test
    public void blockedPieceCannotBePicked() {
        final Piece blockedPiece = makePiece().bamboo().one().at(2, 0, 0);
        final Piece blockingPiece1 = makePiece().bamboo().two().at(0, 0, 0);
        final Piece blockingPiece2 = makePiece().bamboo().two().at(4, 0, 0);

        gameStateBuilder.addOpenPiece(blockingPiece1);
        gameStateBuilder.addOpenPiece(blockingPiece2);
        gameStateBuilder.addBlockedPiece(blockedPiece);

        GameSessionImpl gameSession = gameStateBuilder.createSession();

        gameSession.pickPieceAt(blockedPiece.getCoordinates());

        assertThat("Session picked blocked piece", gameSession, not(hasPickedPieces()));
        assertThat("Session performed a move", gameSession, not(moveWasCompleted()));

    }

    /**
     * Marked tiles list is empty. Picking up selectable tile. Picked tile must
     * be placed to picked tiles list. Move should not complete on this stage.
     */
    @Test
    public void openPieceCanBePicked() {
        final Piece openPiece = makePiece().bamboo().one().at(0, 0, 0);
        final Piece otherPiece = makePiece().bamboo().two().at(2, 0, 0);
        final Piece anotherPiece = makePiece().bamboo().three().at(4, 0, 0);

        gameStateBuilder.addOpenPiece(openPiece);
        gameStateBuilder.addBlockedPiece(otherPiece);
        gameStateBuilder.addOpenPiece(anotherPiece);

        GameSessionImpl gameSession = gameStateBuilder.createSession();

        gameSession.pickPieceAt(openPiece.getCoordinates());

        assertFalse("List of picked pieces should not be empty", gameSession.getPickedPieces().isEmpty());
        assertFalse("Move is not performed on this pick", gameSession.wasMoveCompleted());

        assertThat(gameSession.getPickedPieces(), hasItem(openPiece));
        Assert.assertEquals(1, gameStateBuilder.getPickedPiecesChangedNotifications());

    }

    /**
     * Tests scenario that leads to valid mahjong move.
     */
    @Test
    public void twoOpenPiecesPickedAndMovePerformed() {
        final Piece openPiece = makePiece().bamboo().one().at(0, 0, 0);
        final Piece otherPiece = makePiece().bamboo().two().at(2, 0, 0);
        final Piece anotherPiece = makePiece().bamboo().one().at(4, 0, 0);

        gameStateBuilder.addOpenPiece(openPiece);
        gameStateBuilder.addBlockedPiece(otherPiece);
        gameStateBuilder.addOpenPiece(anotherPiece);
        gameStateBuilder.expectValidMove(openPiece, anotherPiece);

        GameSessionImpl gameSession = gameStateBuilder.createSession();

        gameSession.pickPieceAt(openPiece.getCoordinates());
        gameSession.pickPieceAt(anotherPiece.getCoordinates());

        assertTrue(gameSession.wasMoveCompleted());
        assertTrue(gameStateBuilder.turnCompletedNotified());

    }

    @Test
    public void pickBlockedPieceAfterOpen() {
        final Piece openPiece = makePiece().bamboo().one().at(0, 0, 0);
        final Piece blockedPiece = makePiece().bamboo().two().at(2, 0, 0);
        final Piece anotherPiece = makePiece().bamboo().one().at(4, 0, 0);

        gameStateBuilder.addOpenPiece(openPiece);
        gameStateBuilder.addBlockedPiece(blockedPiece);
        gameStateBuilder.addOpenPiece(anotherPiece);

        GameSessionImpl gameSession = gameStateBuilder.createSession();

        gameSession.pickPieceAt(openPiece.getCoordinates());
        gameSession.pickPieceAt(blockedPiece.getCoordinates());

        assertFalse(gameSession.wasMoveCompleted());
        assertThat(gameSession.getPickedPieces(), hasItem(openPiece));

    }

    @Test
    public void pickTwoOpenPiecesThatDoNotMatch() {
        final Piece openPiece = makePiece().bamboo().one().at(0, 0, 0);
        final Piece blockedPiece = makePiece().bamboo().two().at(2, 0, 0);
        final Piece anotherPiece = makePiece().bamboo().two().at(4, 0, 0);

        gameStateBuilder.addOpenPiece(openPiece);
        gameStateBuilder.addBlockedPiece(blockedPiece);
        gameStateBuilder.addOpenPiece(anotherPiece);
        gameStateBuilder.expectInvalidMove(openPiece, anotherPiece);

        GameSessionImpl gameSession = gameStateBuilder.createSession();

        gameSession.pickPieceAt(openPiece.getCoordinates());
        gameSession.pickPieceAt(anotherPiece.getCoordinates());

        assertFalse(gameSession.wasMoveCompleted());
        assertThat(gameSession.getPickedPieces(), hasItem(anotherPiece));

    }

    @Test
    public void lastPiecesRemovedFromBoard() {
        final Piece openPiece = makePiece().bamboo().one().at(0, 0, 0);
        final Piece anotherPiece = makePiece().bamboo().one().at(4, 0, 0);

        gameStateBuilder.addOpenPiece(openPiece);
        gameStateBuilder.addOpenPiece(anotherPiece);
        gameStateBuilder.expectValidMove(openPiece, anotherPiece);


        GameSessionImpl gameSession = gameStateBuilder.createSession();

        gameSession.pickPieceAt(openPiece.getCoordinates());
        gameSession.pickPieceAt(anotherPiece.getCoordinates());

        assertTrue(gameStateBuilder.gameWonNotified());
        assertTrue(gameSession.isGameWon());
    }

    @Test
    public void noMoreMovesAreAvailable() {

        final Piece a1 = makePiece().bamboo().one().at(0, 0, 0);
        final Piece a2 = makePiece().bamboo().one().at(4, 0, 0);

        final Piece b1 = makePiece().bamboo().two().at(2, 0, 0);
        final Piece b2 = makePiece().bamboo().two().at(6, 0, 0);

        final Piece c1 = makePiece().bamboo().three().at(2, 2, 0);
        final Piece c2 = makePiece().bamboo().three().at(6, 2, 0);

        gameStateBuilder.addOpenPiece(a1);
        gameStateBuilder.addOpenPiece(b2);
        gameStateBuilder.addOpenPiece(a2);
        gameStateBuilder.addOpenPiece(b1);
        gameStateBuilder.addOpenPiece(c1);
        gameStateBuilder.addOpenPiece(c2);
        gameStateBuilder.expectValidMove(c1, c2);

        GameSessionImpl gameSession = gameStateBuilder.createSession();

        gameSession.pickPieceAt(c1.getCoordinates());
        gameSession.pickPieceAt(c2.getCoordinates());

        assertTrue("Move was completed", gameStateBuilder.turnCompletedNotified());
        assertFalse("Game wasn't won", gameStateBuilder.gameWonNotified());
        assertTrue("No other moves available", gameStateBuilder.noMovesLeftNotified());

    }
}
