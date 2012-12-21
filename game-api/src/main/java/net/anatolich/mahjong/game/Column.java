package net.anatolich.mahjong.game;

/**
 * Board column where tiles are placed. Board is divided to columns on which tiles are placed in stacked manner.
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public final class Column {

    public static final int COLUMN_WIDTH = 2;
    public static final int COLUMN_LENGTH = 2;

    protected static boolean isColumnOnTopOfColumn( Column baseColumn, Column column ) {
        return Math.abs(baseColumn.getX() - column.getX()) < COLUMN_WIDTH && Math.abs(baseColumn.getY() - column.getY()) < COLUMN_LENGTH;
    }
    private final int x;
    private final int y;

    public Column( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isOnTopOf( Column baseColumn ) {
        return isColumnOnTopOfColumn(this, baseColumn);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.x;
        hash = 13 * hash + this.y;
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
        final Column other = ( Column ) obj;
        if ( this.x != other.x ) {
            return false;
        }
        if ( this.y != other.y ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Column{" + "x=" + x + ", y=" + y + '}';
    }
}
