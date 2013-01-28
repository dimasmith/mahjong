package net.anatolich.mahjong.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Describes event that happened to pieces on board.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 * @version 1.0
 * @since 1.0
 */
public final class BoardEvent {

    private final Type type;
    private final List<Piece> pieces;
    private final Board board;

    public BoardEvent( Type type, Board board, Piece... pieces ) {
        if ( type == null ) {
            throw new IllegalArgumentException("type parameter cannot be null");
        }
        if ( board == null ) {
            throw new IllegalArgumentException("board parameter cannot be null");
        }
        this.type = type;
        this.pieces = (pieces == null) ? Collections.EMPTY_LIST : new ArrayList<>(Arrays.asList(pieces));
        this.board = board;
    }

    public Type getType() {
        return type;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public Piece getPiece() {
        if ( pieces.isEmpty() ) {
            return null;
        }

        return pieces.get(0);
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "BoardEvent{" + "type=" + type + ", pieces=" + pieces + ", board=" + board + '}';
    }

    public enum Type {

        PIECES_ADDED("Added"),
        PIECES_REMOVED("Removed");
        private final String name;

        private Type( String name ) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Type{" + "name=" + name + '}';
        }
    }
}
