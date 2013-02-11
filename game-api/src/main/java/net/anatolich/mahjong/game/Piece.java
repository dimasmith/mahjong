package net.anatolich.mahjong.game;

import java.util.Objects;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class Piece {

    private Tile tile;
    private Coordinates coordinates;

    public Piece() {
    }

    public Piece( Tile tile, Coordinates coordinates ) {
        this.tile = tile;
        this.coordinates = coordinates;
    }
    
    Piece( Tile.Type type, Tile.Value value, int x, int y, int z ) {
        this(new Tile(type, value), new Coordinates(x, y, z));
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile( Tile tile ) {
        this.tile = tile;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates( Coordinates coordinates ) {
        this.coordinates = coordinates;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.tile);
        hash = 53 * hash + Objects.hashCode(this.coordinates);
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
        final Piece other = ( Piece ) obj;
        if ( !Objects.equals(this.tile, other.tile) ) {
            return false;
        }
        if ( !Objects.equals(this.coordinates, other.coordinates) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Piece{" + "tile=" + tile + ", coordinates=" + coordinates + '}';
    }
}
