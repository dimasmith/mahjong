package net.anatolich.mahjong.game;

import java.util.Iterator;
import net.anatolich.mahjong.game.impl.BoardImpl;
import net.anatolich.mahjong.game.impl.DefaultTileSet;
import net.anatolich.mahjong.game.layout.LayoutImpl;
import net.anatolich.mahjong.game.layout.LayoutFactory;
import net.anatolich.mahjong.game.layout.Slot;

/**
 * Game session keeps track of game and allows player to interact with game.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class GameSession {

    private final BoardImpl board;

    private GameSession() {
        board = new BoardImpl();
        LayoutImpl layout = new LayoutFactory().getDefaultLayout();
        TileSet tileSet = new DefaultTileSet();
        Iterator<Tile> tileIterator = tileSet.getTiles().iterator();
        for ( Slot slot : layout.getSlots()) {
            Tile tile = tileIterator.next();
            board.putPiece(new Piece(tile, slot.getCoordinates()));
        }

    }

    public static GameSession startGame() {
        return new GameSession();
    }

    public Board getBoard() {
        return board;
    }
}
