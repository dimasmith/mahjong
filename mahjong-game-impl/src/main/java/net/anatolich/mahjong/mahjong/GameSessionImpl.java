package net.anatolich.mahjong.mahjong;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import net.anatolich.mahjong.game.AvailableMove;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.GameEvent;
import net.anatolich.mahjong.game.GameSession;
import net.anatolich.mahjong.game.GameSessionListener;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.rules.Rules;
import net.anatolich.mahjong.game.spi.MutableBoard;

/**
 * Game session keeps track of game and allows player to interact with game.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class GameSessionImpl implements GameSession {

    private final MutableBoard board;
    private final Rules rules;
    private boolean moveWasCompleted = false;
    private Piece startPiece;
    private Piece endPiece;
    private final AvailableMovesCollector availableMovesCollector;
    private List<AvailableMove> availableMoves;
    private final List<GameSessionListener> listeners;

    public GameSessionImpl(MutableBoard board, Rules rules) {
        this.board = board;
        this.rules = rules;
        this.availableMovesCollector = new AvailableMovesCollector(board, rules);
        listeners = new LinkedList<>();
        calculateAvailableMoves();
    }

    GameSessionImpl(MutableBoard board, Rules rules, AvailableMovesCollector availableMovesCollector) {
        this.board = board;
        this.rules = rules;
        this.availableMovesCollector = availableMovesCollector;
        listeners = new LinkedList<>();
        calculateAvailableMoves();
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public List<Piece> getPickedPieces() {
        return (noPickedPieces()) ? Collections.EMPTY_LIST : Collections.singletonList(startPiece);
    }

    @Override
    public boolean wasMoveCompleted() {
        return moveWasCompleted;
    }

    @Override
    public void pickPieceAt(Coordinates coordinates) {
        moveWasCompleted = false;
        final Piece piece = board.getPieceAt(coordinates);

        if (noPickedPieces()) {
            if (rules.isPieceOpen(coordinates, board)) {
                pickPiece(piece);
            }
        } else {
            if (rules.isMoveLegal(getPickedPiece().getCoordinates(), piece.getCoordinates(), board)) {
                endPiece = piece;
                completeMove();
            } else {
                if (rules.isPieceOpen(coordinates, board)) {
                    pickPiece(piece);
                }
            }
        }
    }

    @Override
    public boolean hasMoreMoves() {
        return !getAvailableMoves().isEmpty();
    }

    @Override
    public boolean isGameEnded() {
        return board.getAllPieces().isEmpty();
    }

    @Override
    public List<AvailableMove> getAvailableMoves() {
        return availableMoves;
    }

    public void setPickedPiece(Piece piece) {
        pickPiece(piece);
    }

    void completeMove() {
        if (endPiece == null || startPiece == null) {
            throw new IllegalStateException("Either start or end piece is not selected");
        }
        final Piece startPieceRef = startPiece;
        final Piece endPieceRef = endPiece;

        this.startPiece = null;
        this.endPiece = null;
        moveWasCompleted = true;

        board.removePieceAt(startPieceRef.getCoordinates());
        board.removePieceAt(endPieceRef.getCoordinates());
        fireMoveCompletedEvent(startPieceRef, endPieceRef);

        if (board.getAllPieces().isEmpty()) {
            fireGameWonEvent();
        } else {
            calculateAvailableMoves();
            if (availableMoves.isEmpty()){
                fireNoMovesLeftEvent();
            }
        }
    }

    private void pickPiece(final Piece piece) {
        startPiece = piece;
        firePiecePickedNotification();
    }

    private boolean noPickedPieces() {
        return getPickedPiece() == null;
    }

    private Piece getPickedPiece() {
        return startPiece;
    }

    @Override
    public String toString() {
        return "GameSessionImpl{" + "moveWasCompleted=" + moveWasCompleted + ", pickedPiece=" + startPiece + '}';
    }

    private void calculateAvailableMoves() {
        this.availableMoves = availableMovesCollector.collectMoves();
    }

    @Override
    public void addListener(GameSessionListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(GameSessionListener listener) {
        listeners.remove(listener);
    }

    private void firePiecePickedNotification() {
        final GameEvent event = new GameEvent(GameEvent.Type.SELECTION_CHANGED, this, startPiece);
        firePickedPieceChangedEvent(event);
    }

    private void fireMoveCompletedEvent(Piece startPiece, Piece endPiece) {
        final GameEvent event = new GameEvent(GameEvent.Type.TURN_COMPLETED, this, startPiece, endPiece);
        fireTurnCompletedEvent(event);
    }

    private void firePickedPieceChangedEvent(final GameEvent event) {
        for (GameSessionListener gameSessionListener : listeners) {
            gameSessionListener.pickedPiecesChanged(event);
        }
    }

    private void fireTurnCompletedEvent(final GameEvent event) {
        for (GameSessionListener gameSessionListener : listeners) {
            gameSessionListener.turnCompleted(event);
        }
    }

    /**
     * For testing purposes only
     *
     * @param startPiece
     */
    void setStartPiece(Piece startPiece) {
        this.startPiece = startPiece;
    }

    /**
     * For testing purposes only
     *
     * @param endPiece
     */
    void setEndPiece(Piece endPiece) {
        this.endPiece = endPiece;
    }

    private void fireGameWonEvent() {
        for (GameSessionListener listener : listeners) {
            listener.gameWon();
        }
    }

    private void fireNoMovesLeftEvent() {
        for (GameSessionListener listener : listeners) {
            listener.noMovesLeft();
        }
    }
}
