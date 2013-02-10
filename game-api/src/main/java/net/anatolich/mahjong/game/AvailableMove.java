package net.anatolich.mahjong.game;

import java.util.Objects;

/**
 * Describes valid move containing start and end piece for move.
 *
 * @author Dmytro
 * @since 1.0
 * @version 1.0
 */
public class AvailableMove {

    private final Piece startPiece, endPiece;

    public AvailableMove(Piece startPiece, Piece endPiece) {
        this.startPiece = startPiece;
        this.endPiece = endPiece;
    }

    public Piece getStartPiece() {
        return startPiece;
    }

    public Piece getEndPiece() {
        return endPiece;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.startPiece);
        hash = 37 * hash + Objects.hashCode(this.endPiece);
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
        final AvailableMove other = (AvailableMove) obj;
        if (!Objects.equals(this.startPiece, other.startPiece)) {
            return false;
        }
        if (!Objects.equals(this.endPiece, other.endPiece)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AvailableMove{" + "startPiece=" + startPiece + ", endPiece=" + endPiece + '}';
    }
}
