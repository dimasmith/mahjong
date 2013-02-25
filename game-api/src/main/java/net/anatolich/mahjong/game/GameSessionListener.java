package net.anatolich.mahjong.game;

/**
 * Listens to game session events. 
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 * @since 1.0
 * @version 1.0
 */
public interface GameSessionListener {

    void pickedPiecesChanged( GameEvent event );

    void turnCompleted( GameEvent event );

    void noMovesLeft();

    void gameWon();
}
