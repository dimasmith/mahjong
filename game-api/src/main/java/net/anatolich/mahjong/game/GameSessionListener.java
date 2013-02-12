package net.anatolich.mahjong.game;

/**
 * Listens to game session events.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public interface GameSessionListener {

    void pickedPiecesChanged( GameEvent event );

    void turnCompleted( GameEvent event );

    void noMovesLeft();

    void gameWon();
}
