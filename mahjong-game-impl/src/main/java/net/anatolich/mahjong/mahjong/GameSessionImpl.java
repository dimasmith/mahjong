package net.anatolich.mahjong.mahjong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.GameSession;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.Tile;
import net.anatolich.mahjong.game.impl.BoardFiller;
import net.anatolich.mahjong.game.impl.DefaultBoard;
import net.anatolich.mahjong.game.impl.DefaultTileSet;
import net.anatolich.mahjong.game.layout.LayoutFactory;
import net.anatolich.mahjong.game.layout.LayoutImpl;
import net.anatolich.mahjong.game.layout.Slot;
import net.anatolich.mahjong.game.spi.TileSet;

/**
 * Game session keeps track of game and allows player to interact with game.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class GameSessionImpl implements GameSession {

    private final DefaultBoard board;

    public GameSessionImpl() {
        board = new DefaultBoard();
        LayoutImpl layout = new LayoutFactory().getDefaultLayout();
        TileSet tileSet = new DefaultTileSet();
        new BoardFiller(board).fill(layout, tileSet);
    }

    public static GameSessionImpl startGame() {
        return new GameSessionImpl();
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public List<Piece> getPickedPieces() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean wasMoveCompleted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void pickPieceAt( Coordinates coordinates ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasMoreMoves() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isGameEnded() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
