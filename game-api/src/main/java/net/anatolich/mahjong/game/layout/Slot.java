package net.anatolich.mahjong.game.layout;

import java.io.Serializable;
import java.util.Objects;
import net.anatolich.mahjong.game.Coordinates;

/**
 * Marks place in layout where it is appropriate to put pieces.
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public final class Slot implements Serializable {

    private static final long serialVersionUID = 0x1L;

    public static final int SLOT_WIDTH = 2;
    public static final int SLOT_LENGTH = 2;

    private final Coordinates coordinates;

    public Slot( Coordinates coordinates ) {
        if (coordinates == null){
            throw new IllegalArgumentException("coordinates argument must not be null");
        }
        this.coordinates = coordinates;
    }

    public boolean intersectsWith( Slot otherSlot ) {
        Coordinates otherSlotCoordinates = otherSlot.getCoordinates();

        int dX = Math.abs(otherSlotCoordinates.getX() - coordinates.getX());
        int dY = Math.abs(otherSlotCoordinates.getY() - coordinates.getY());

        return dX < SLOT_WIDTH && dY < SLOT_LENGTH && ( coordinates.getLayer() == otherSlotCoordinates.getLayer() );
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.coordinates);
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Slot other = ( Slot ) obj;
        if ( !Objects.equals(this.coordinates, other.getCoordinates()) ) {
            return false;
        }
        return true;
    }
}
