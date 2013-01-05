package net.anatolich.mahjong.game;

import java.util.Objects;

/**
 * Slot is a coordinate-based holder for tiles on board.
 * Slots are placed on board according to layout. At the beginning of the game slots are filled with tiles.
 * On successful move slots that are involved in move are removed from board.
 * When board became shuffled all slots are cleared and than refilled with tiles in random order.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public final class Slot {

    public static final int SLOT_WIDTH = 2;
    public static final int SLOT_LENGTH = 2;
    private final Coordinates coordinates;
    private Tile tile;

    public Slot( Coordinates coordinates ) {
        this(coordinates, null);
    }

    public Slot( Coordinates coordinates, Tile tile ) {
        if ( coordinates == null ) {
            throw new IllegalArgumentException("coordinates parameter must not be null");
        }
        this.coordinates = coordinates;
        this.tile = tile;
    }

    public boolean intersectsWith( Slot otherSlot ) {
        Coordinates otherSlotCoordinates = otherSlot.getCoordinates();

        int dX = Math.abs(otherSlotCoordinates.getX() - coordinates.getX());
        int dY = Math.abs(otherSlotCoordinates.getY() - coordinates.getY());

        return dX < SLOT_WIDTH && dY < SLOT_LENGTH && ( coordinates.getLayer() == otherSlotCoordinates.getLayer() );
    }

    public boolean isEmpty() {
        return tile == null;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setTile( Tile tile ) {
        this.tile = tile;
    }

    public void removeTile() {
        this.tile = null;
    }

    public Tile getTile() {
        return tile;
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
