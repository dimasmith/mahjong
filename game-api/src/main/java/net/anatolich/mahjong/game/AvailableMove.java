package net.anatolich.mahjong.game;

import java.util.Objects;

/**
 * Describes valid move containing start and end piece for move.
 * Available move should not care about pieces order inside it. 
 * For example move with start piece of Bamboo One at 0,0,0 and end piece of Bamboo One at 8,0,0
 * must be equal to one with start and end pieces swapped.
 * It is also illegal to create AvailableMove instance for two pieces with equal coordinates.
 * @author Dmytro
 * @since 1.0
 * @version 1.0
 */
public class AvailableMove {

    private final Piece startPiece, endPiece;

    public AvailableMove(Piece startPiece, Piece endPiece) {
        if (startPiece.getCoordinates().equals(endPiece.getCoordinates())){
            throw new IllegalStateException("Piece coordinates must not be equal");
        }
        this.startPiece = startPiece;
        this.endPiece = endPiece;
    }

    public Piece getStartPiece() {
        return startPiece;
    }

    public Piece getEndPiece() {
        return endPiece;
    }
    
    private AvailableMove invert(){
        return new AvailableMove(endPiece, startPiece);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (Objects.hashCode(this.startPiece) + Objects.hashCode(this.endPiece));
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
        AvailableMove otherMove = (AvailableMove) obj;
        if (!Objects.equals(this.startPiece, otherMove.startPiece)) {            
            otherMove = otherMove.invert();
        }
        if (!Objects.equals(this.startPiece, otherMove.startPiece)) {            
            return false;
        }
        if (!Objects.equals(this.endPiece, otherMove.endPiece)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AvailableMove{" + "startPiece=" + startPiece + ", endPiece=" + endPiece + '}';
    }
}
