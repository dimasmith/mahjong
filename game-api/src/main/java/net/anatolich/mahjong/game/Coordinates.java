package net.anatolich.mahjong.game;

/**
 * Three-dimensional coordinates where tiles can be placed.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class Coordinates {

    private final int x;
    private final int y;
    private final int layer;

    public Coordinates( int x, int y, int layer ) {
        this.x = x;
        this.y = y;
        this.layer = layer;
    }

    public Coordinates( Column column, int layer ) {
        this(column.getX(), column.getY(), layer);
    }

    public boolean isOnColumn(Column column){
        return column.getX() == x && column.getY() == y;
    }

    public Column getColumn(){
        return new Column(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLayer() {
        return layer;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.x;
        hash = 59 * hash + this.y;
        hash = 59 * hash + this.layer;
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
        final Coordinates other = ( Coordinates ) obj;
        if ( this.x != other.x ) {
            return false;
        }
        if ( this.y != other.y ) {
            return false;
        }
        if ( this.layer != other.layer ) {
            return false;
        }
        return true;
    }

    public Coordinates translate( int tx, int ty, int tz ) {
        return new Coordinates(x + tx, y + ty, layer + tz);
    }

    public Coordinates translate( Coordinates translation ) {
        return new Coordinates(x + translation.getX(), y + translation.getY(), layer + translation.getLayer());
    }
}
