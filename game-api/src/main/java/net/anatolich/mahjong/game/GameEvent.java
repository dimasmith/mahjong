package net.anatolich.mahjong.game;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 11 * hash + Objects.hashCode(this.pieces);
        hash = 11 * hash + Objects.hashCode(this.source);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GameEvent other = (GameEvent) obj;
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.pieces, other.pieces)) {
            return false;
        }
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GameEvent{" + "type=" + type + ", pieces=" + pieces + ", source=" + source + '}';
    }
}
