package net.anatolich.mahjong.game;

import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class GameEvent {

    public enum Type {

        SELECTION_CHANGED,
        TURN_COMPLETED
    }
    private final Type type;
    private final Collection<Piece> pieces;
    private final GameSession source;

    public GameEvent(Type type, GameSession source, Collection<Piece> pieces) {
        this.type = type;
        this.pieces = pieces;
        this.source = source;
    }

    public GameEvent(Type type, GameSession source, Piece... pieces) {
        this(type, source, Arrays.asList(pieces));
    }

    public Type getType() {
        return type;
    }

    public Collection<Piece> getPieces() {
        return pieces;
    }

    public GameSession getSource() {
        return source;
    }
}
