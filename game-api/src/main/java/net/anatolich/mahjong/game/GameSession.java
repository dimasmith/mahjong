package net.anatolich.mahjong.game;

import java.util.List;
import net.anatolich.mahjong.game.capabilities.Capabilities;

/**
 * Controls game session from start to end.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 * @since 1.0
 * @version 1.0
 */
public interface GameSession {

    Board getBoard();

    List<Piece> getPickedPieces();

    boolean wasMoveCompleted();

    void pickPieceAt(Coordinates coordinates);

    boolean hasMoreMoves();

    boolean isGameEnded();

    void addListener(GameSessionListener listener);

    void removeListener(GameSessionListener listener);

    Capabilities capabilities();
}
