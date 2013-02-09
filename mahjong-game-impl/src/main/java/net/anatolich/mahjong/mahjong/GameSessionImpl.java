package net.anatolich.mahjong.mahjong;

import java.util.Collections;
import java.util.List;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.GameSession;
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
    private Piece pickedPiece;

    public GameSessionImpl( MutableBoard board, Rules rules) {
        this.board = board;
        this.rules = rules;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public List<Piece> getPickedPieces() {
        return ( noPickedPieces() ) ? Collections.EMPTY_LIST : Collections.singletonList(pickedPiece);
    }

    @Override
    public boolean wasMoveCompleted() {
        return moveWasCompleted;
    }

    @Override
    public void pickPieceAt( Coordinates coordinates ) {
        moveWasCompleted = false;
        final Piece piece = board.getPieceAt(coordinates);

        if ( noPickedPieces() ) {
            if ( rules.isPieceOpen(coordinates, board) ) {
                pickPiece(piece);
            }
        } else {
            if ( rules.isMoveLegal(getPickedPiece().getCoordinates(), piece.getCoordinates(), board) ) {
                completeMove(piece);
            } else {
                if ( rules.isPieceOpen(coordinates, board) ) {
                    pickPiece(piece);
                }
            }
        }
    }

    @Override
    public boolean hasMoreMoves() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isGameEnded() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPickedPiece( Piece piece ) {
        pickPiece(piece);
    }

    private void completeMove( final Piece piece ) {
        moveWasCompleted = true;
        board.removePieceAt(getPickedPiece().getCoordinates());
        board.removePieceAt(piece.getCoordinates());
        this.pickedPiece = null;
    }

    private void pickPiece( final Piece piece ) {
        pickedPiece = piece;
    }

    private boolean noPickedPieces() {
        return getPickedPiece() == null;
    }

    private Piece getPickedPiece() {
        return pickedPiece;
    }

    @Override
    public String toString() {
        return "GameSessionImpl{" + "moveWasCompleted=" + moveWasCompleted + ", pickedPiece=" + pickedPiece + '}';
    }
}
