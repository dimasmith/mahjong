package net.anatolich.mahjong.game;

/**
 * Listens to events that happened to pieces on board.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 * @version 1.0
 * @since 1.0
 */
public interface BoardListener {

    /**
     * Triggered on each board change.
     * @param evt event object that describes changes on board.
     */
    void boardChanged( BoardEvent evt );
}
