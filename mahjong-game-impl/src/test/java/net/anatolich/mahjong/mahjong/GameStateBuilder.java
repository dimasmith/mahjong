package net.anatolich.mahjong.mahjong;

import java.util.ArrayList;
import java.util.List;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.GameEvent;
import net.anatolich.mahjong.game.GameSessionListener;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.rules.Rules;
import net.anatolich.mahjong.game.spi.MutableBoard;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import org.easymock.IAnswer;

/**
 *
 * @author dmytro
 */
public class GameStateBuilder {

    private final List<Piece> pieces;
    private final List<Coordinates> coordinates;
    private final MutableBoard board;
    private final Rules rules;
    private final GameSessionListener listener;
    private boolean gameWonNotified = false;
    private boolean turnCompletedNotified = false;
    private boolean noMovesLeftNotified = true;
    private int pickedPiecesChangedNotifications = 0;

    public GameStateBuilder() {
        this.pieces = new ArrayList<>();
        this.coordinates = new ArrayList<>();

        board = createNiceMock("board", MutableBoard.class);
        rules = createNiceMock("rules", Rules.class);
        listener = new GameSessionListener() {
            @Override
            public void pickedPiecesChanged(GameEvent event) {
                pickedPiecesChangedNotifications++;
            }

            @Override
            public void turnCompleted(GameEvent event) {
                turnCompletedNotified = true;
            }

            @Override
            public void noMovesLeft() {
                noMovesLeftNotified = true;
            }

            @Override
            public void gameWon() {
                gameWonNotified = true;
            }
        };

    }

    public void addOpenPiece(Piece piece) {
        addPiece(piece);

        expect(rules.isPieceOpen(piece.getCoordinates(), board)).andReturn(true).anyTimes();
        expect(board.getPieceAt(piece.getCoordinates())).andReturn(piece).anyTimes();

    }

    public void addBlockedPiece(Piece piece) {
        addPiece(piece);
        expect(rules.isPieceOpen(piece.getCoordinates(), board)).andReturn(false).anyTimes();
        expect(board.getPieceAt(piece.getCoordinates())).andReturn(piece).anyTimes();
    }

    public GameSessionImpl createSession() {
        expect(board.getAllPieces()).andReturn(pieces).anyTimes();

        replay(board, rules);

        final GameSessionImpl gameSession = new GameSessionImpl(board, rules);
        gameSession.addListener(listener);

        return gameSession;
    }

    private void addPiece(Piece piece) {
        pieces.add(piece);
        coordinates.add(piece.getCoordinates());
    }

    public void expectValidMove(final Piece startPiece, final Piece endPiece) {
        expectRemovePiece(startPiece);
        expectRemovePiece(endPiece);

        expect(rules.isMoveLegal(startPiece.getCoordinates(), endPiece.getCoordinates(), board)).andReturn(true).anyTimes();
    }

    public void expectInvalidMove(Piece startPiece, Piece endPiece) {
        expect(rules.isMoveLegal(startPiece.getCoordinates(), endPiece.getCoordinates(), board)).andReturn(false).anyTimes();
    }

    private void expectRemovePiece(final Piece piece) {
        board.removePieceAt(piece.getCoordinates());
        EasyMock.expectLastCall().andAnswer(new IAnswer<Object>() {
            @Override
            public Object answer() throws Throwable {
                pieces.remove(piece);
                coordinates.remove(piece.getCoordinates());
                return null;
            }
        });
    }

    public boolean gameWonNotified() {
        return gameWonNotified;
    }

    public boolean turnCompletedNotified() {
        return turnCompletedNotified;
    }

    public boolean noMovesLeftNotified() {
        return noMovesLeftNotified;
    }

    public int getPickedPiecesChangedNotifications() {
        return pickedPiecesChangedNotifications;
    }
}
