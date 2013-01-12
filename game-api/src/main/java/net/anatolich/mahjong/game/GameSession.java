package net.anatolich.mahjong.game;

import net.anatolich.mahjong.game.layout.LayoutImpl;
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

        LayoutImpl layout = new LayoutFactory().getDefaultLayout();


    }

    public static GameSession startGame() {
        return new GameSession();
    }

    public Board getBoard() {
        return board;
    }
}
