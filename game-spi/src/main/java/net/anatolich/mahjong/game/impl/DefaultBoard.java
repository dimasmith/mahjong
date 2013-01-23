package net.anatolich.mahjong.game.impl;

import net.anatolich.mahjong.game.spi.MutableBoard;
import net.anatolich.mahjong.game.spi.FillableBoard;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Column;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;

/**
 * Board that can be viewed and changed. Game clients are using narrower interface for board ({@link Board}) that allows
 * only viewing of the pieces.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 * @version 1.0
 * @since 1.0
 */
public class DefaultBoard implements MutableBoard {

    private final Map<Coordinates, Piece> pieces;

    public DefaultBoard() {
        this.pieces = new HashMap<>();
    }

    @Override
    public List<Piece> getAllPieces() {
        return new ArrayList<>(pieces.values());
    }

    @Override
    public Piece getTopmostPieceAt( int x, int y ) {
        int topLayer = -1;
        final Column expectedColumn = new Column(x, y);
        for ( Piece piece : pieces.values() ) {
            final Coordinates coordinates = piece.getCoordinates();

            if ( coordinates.isOnColumn(expectedColumn) ) {
                if ( topLayer < coordinates.getLayer() ) {
                    topLayer = coordinates.getLayer();
                }
            }
        }

        return pieces.get(new Coordinates(x, y, topLayer));
    }

    @Override
    public Piece getPieceAt( Coordinates coordinates ) {
        return pieces.get(coordinates);
    }

    @Override
    public void putPiece( Piece piece ) {
        pieces.put(piece.getCoordinates(), piece);
    }

    @Override
    public void putPieces( Collection<Piece> pieces ) {
        // TODO must invoke event firing only once and pass all affected pieces to event.
        for ( Piece piece : pieces ) {
            this.pieces.put(piece.getCoordinates(), piece);
        }
    }

    @Override
    public void removePieceAt( Coordinates coordinates ) {
        pieces.remove(coordinates);
    }
}
