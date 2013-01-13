package net.anatolich.mahjong.game.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.Column;
import net.anatolich.mahjong.game.Coordinates;
import net.anatolich.mahjong.game.Piece;

/**
 * Game board containing tiles placed on it's positions. Board can be viewed by player. GameMaster can view board and
 * remove tiles from it as well as shuffling it.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class BoardImpl implements Board {

    private final Map<Coordinates, Piece> pieces;

    public BoardImpl() {
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

    public Piece getPieceAt( Coordinates coordinates ) {
        return pieces.get(coordinates);
    }

    void putPiece( Piece piece ) {
        pieces.put(piece.getCoordinates(), piece);
    }
}
