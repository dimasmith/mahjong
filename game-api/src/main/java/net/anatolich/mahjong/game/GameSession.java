package net.anatolich.mahjong.game;

import net.anatolich.mahjong.game.layout.Layout;
import net.anatolich.mahjong.game.layout.LayoutFactory;

/**
 * Game session keeps track of game and allows player to interact with game.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class GameSession {

    private final Board board;

    private GameSession() {
        board = new Board();

        Layout layout = new LayoutFactory().getDefaultLayout();

        
    }

    public static GameSession startGame() {
        return new GameSession();
    }

    public Board getBoard() {
        return board;
    }
}
