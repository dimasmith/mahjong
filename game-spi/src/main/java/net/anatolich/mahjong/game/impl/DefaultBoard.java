package net.anatolich.mahjong.game.impl;

import net.anatolich.mahjong.game.spi.MutableBoard;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.anatolich.mahjong.game.Board;
import net.anatolich.mahjong.game.BoardEvent;
import net.anatolich.mahjong.game.BoardListener;
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
    private final List<BoardListener> listeners;

    public DefaultBoard() {
        this.listeners = Collections.synchronizedList(new LinkedList<BoardListener>());
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
        firePieceAddedEvent(piece);
    }

    @Override
    public void putPieces( Collection<Piece> pieces ) {
        for ( Piece piece : pieces ) {
            this.pieces.put(piece.getCoordinates(), piece);
        }
        firePieceAddedEvent(pieces.toArray(new Piece[0]));
    }

    @Override
    public void removePieceAt( Coordinates coordinates ) {
        Piece piece = pieces.get(coordinates);
        pieces.remove(coordinates);
        if ( piece != null ) {
            firePieceRemovedEvent(piece);
        }
    }

    @Override
    public void addChangeListener( BoardListener listener ) {
        listeners.add(listener);
    }

    @Override
    public void removeChangeListener( BoardListener listener ) {
        listeners.remove(listener);
    }

    protected void firePieceAddedEvent( Piece... pieces ) {
        fireBoardEvent(new BoardEvent(BoardEvent.Type.PIECES_ADDED, this, pieces));
    }

    protected void firePieceRemovedEvent( Piece... pieces ) {
        fireBoardEvent(new BoardEvent(BoardEvent.Type.PIECES_REMOVED, this, pieces));
    }

    protected void fireBoardEvent( final BoardEvent evt ) {
        for ( BoardListener boardListener : listeners ) {
            boardListener.boardChanged(evt);
        }
    }
}
