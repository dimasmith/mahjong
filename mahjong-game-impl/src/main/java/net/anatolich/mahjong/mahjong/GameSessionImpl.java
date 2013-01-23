package net.anatolich.mahjong.mahjong;

import java.util.Iterator;
import java.util.List;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.GameSession;
import net.anatolich.mahjong.game.Piece;
import net.anatolich.mahjong.game.Tile;
import net.anatolich.mahjong.game.impl.BoardImpl;
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

    private final BoardImpl board;

    public GameSessionImpl() {
        board = new BoardImpl();
        LayoutImpl layout = new LayoutFactory().getDefaultLayout();
        TileSet tileSet = new DefaultTileSet();
        Iterator<Tile> tileIterator = tileSet.getTiles().iterator();
        for ( Slot slot : layout.getSlots()) {
            Tile tile = tileIterator.next();
            board.putPiece(new Piece(tile, slot.getCoordinates()));
        }

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
