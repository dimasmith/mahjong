package net.anatolich.mahjong.game;

import java.util.List;

/**
 * Describes game.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public interface Game {

    String getName();

    /**
     * Starts game with default board layout
     * @return
     */
    GameSession startGame();

    /**
     * Starts game using provided board layout.
     * @param layout layout from list of supported by game
     * @return
     */
    GameSession startGame(Layout layout);

    /**
     * Lists all layouts supported by this game.
     * @return
     */
    List<Layout> supportedLayouts();
}
