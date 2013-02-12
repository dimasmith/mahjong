package net.anatolich.mahjong.game;

import java.util.List;

/**
 * Controls game session from start to end.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public interface GameSession {

    Board getBoard();

    List<Piece> getPickedPieces();

    boolean wasMoveCompleted();

    void pickPieceAt(Coordinates coordinates);

    boolean hasMoreMoves();

    boolean isGameEnded();

    List<AvailableMove> getAvailableMoves();

    void addListener(GameSessionListener listener);

    void removeListener(GameSessionListener listener);
}
